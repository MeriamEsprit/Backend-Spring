package tn.esprit.spring.controllers;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.request.ChangePwdDto;
import tn.esprit.spring.Dto.response.EtudiantDto;
import tn.esprit.spring.Dto.request.SignupRequest;
import tn.esprit.spring.Dto.response.MessageResponse;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.CompetenceRepository;
import tn.esprit.spring.services.ClasseServicesImpl;
import tn.esprit.spring.services.EmailService;
import tn.esprit.spring.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UtilisateurRepository userRepository;

    @Autowired
    ClasseRepository classeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ClasseServicesImpl classeService;

    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        try {
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Email déjà utilisé!"));
            }

            // Create new user's account
            Utilisateur user = new Utilisateur();
            user.setCin(signUpRequest.getCin());
            user.setNom(signUpRequest.getNom());
            user.setPrenom(signUpRequest.getPrenom());
            user.setEmail(signUpRequest.getEmail());
            user.setPrivateemail(signUpRequest.getPrivateemail());
            user.setGender(signUpRequest.getGender());
            user.setDateofbirth(signUpRequest.getDateofbirth());
            user.setStarteducation(signUpRequest.getStarteducation());
            if (signUpRequest.getRole().equals("TEACHER")) {
                if (signUpRequest.getCompetence() != null && !signUpRequest.getCompetence().isEmpty()) {
                    Long competenceId = Long.valueOf(signUpRequest.getCompetence());
                    Optional<Competence> competenceOpt = competenceRepository.findById(competenceId);

                    if (competenceOpt.isPresent()) {
                        Competence competence = competenceOpt.get();
                        if (competence.getNomCompetence() != null && !competence.getNomCompetence().isEmpty()) {
                            user.setCompetence(competence);
                        }
                    }
                }
                user.setRole(ERole.ROLE_TEACHER);
            } else if (signUpRequest.getRole().equals("STUDENT")) {
                if (signUpRequest.getClasse() != null && !signUpRequest.getClasse().isEmpty()) {
                    Long classeId = Long.valueOf(signUpRequest.getClasse());
                    Optional<Classe> classeOpt = classeRepository.findById(classeId);

                    if (classeOpt.isPresent()) {
                        Classe classe = classeOpt.get();
                        if (classe.getNomClasse() != null && !classe.getNomClasse().isEmpty()) {
                            user.setClasse(classe);
                        }
                    }
                }
                user.setRole(ERole.ROLE_STUDENT);
            }
            userRepository.save(user);

            user.setIdentifiant(generateIdentifiant(user.getId(), user.getRole()));
            user.setMotDePasse(encoder.encode(user.getIdentifiant()));
            userRepository.save(user);
            try {
                if (!user.getPrivateemail().isEmpty()) {
                    String subject = "Bienvenue dans Esprit";
                    String content = String.format(
                            "Bonjour %s %s,\n\n" +
                                    "Bienvenue dans Esprit !\n\n" +
                                    "Voici vos identifiants de compte :\n" +
                                    "Email : %s\n\n" +
                                    "Identifiant : %s\n\n" +
                                    "Merci de vous être inscrit chez nous.\n\n" +
                                    "Cordialement,\n" +
                                    "L'équipe Esprit",
                            user.getNom(), user.getPrenom(), user.getEmail(), user.getIdentifiant()
                    );
                    emailService.sendEmail(user.getPrivateemail(), subject, content);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody SignupRequest signUpRequest) {
        try {
            // Récupérer l'utilisateur existant
            Optional<Utilisateur> userOpt = userRepository.findById(id);
            if (!userOpt.isPresent()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            Utilisateur user = userOpt.get();

            if (userRepository.existsByEmail(signUpRequest.getEmail()) && !signUpRequest.getEmail().equals(user.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Email déjà utilisé!"));
            }

            if (!signUpRequest.getCin().isEmpty()) user.setCin(signUpRequest.getCin());
            if (!signUpRequest.getNom().isEmpty()) user.setNom(signUpRequest.getNom());
            if (!signUpRequest.getPhoto().isEmpty()) user.setPhoto(signUpRequest.getPhoto());
            else user.setPhoto("");
            if (!signUpRequest.getPrenom().isEmpty()) user.setPrenom(signUpRequest.getPrenom());
            if (!signUpRequest.getEmail().isEmpty()) user.setEmail(signUpRequest.getEmail());
            if (!signUpRequest.getPrivateemail().isEmpty()) user.setPrivateemail(signUpRequest.getPrivateemail());
            if (!signUpRequest.getDateofbirth().isEmpty()) user.setDateofbirth(signUpRequest.getDateofbirth());
            if (!signUpRequest.getGender().isEmpty()) user.setGender(signUpRequest.getGender());
            if (!signUpRequest.getStarteducation().isEmpty()) user.setStarteducation(signUpRequest.getStarteducation());
            if(!signUpRequest.getHidden().isEmpty())user.setHidden(Boolean.parseBoolean(signUpRequest.getHidden()));
            if(user.isHidden()==false){
                user.setDisabled(false);
            }

            if (signUpRequest.getClasse() != null && !signUpRequest.getClasse().isEmpty()) {
                Long classeId = Long.valueOf(signUpRequest.getClasse());
                Optional<Classe> classeOpt = classeRepository.findById(classeId);

                if (classeOpt.isPresent()) {
                    Classe classe = classeOpt.get();
                    if (classe.getNomClasse() != null && !classe.getNomClasse().isEmpty()) {
                        user.setClasse(classe);
                    }
                }
            }

            if (signUpRequest.getCompetence() != null && !signUpRequest.getCompetence().isEmpty()) {
                Long competenceId = Long.valueOf(signUpRequest.getCompetence());
                Optional<Competence> competenceOpt = competenceRepository.findById(competenceId);

                if (competenceOpt.isPresent()) {
                    Competence competence = competenceOpt.get();
                    if (competence.getNomCompetence() != null && !competence.getNomCompetence().isEmpty()) {
                        user.setCompetence(competence);
                    }
                }
            }

            userRepository.save(user);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generateIdentifiant(Long userId, ERole role) {
        int year = LocalDate.now().getYear() % 1000;
        if (role == ERole.ROLE_STUDENT) {
            return String.format("%dSMT%03d", year, userId);
        }
        if (role == ERole.ROLE_TEACHER) {
            return String.format("%dTMT%03d", year, userId);
        }
        return "0000";
    }

    @GetMapping("/profile")
    //    /api/user/profile
    public ResponseEntity<?> getMyProfile() {
//        try {
//            Utilisateur userInfoDto =
//            return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
//        } catch (EntityNotFoundException ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//        } catch (RuntimeException ex) {
//            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        try {
            Utilisateur etudiant = userService.getInfo();
            Long competenceId = etudiant.getCompetence() != null ? etudiant.getCompetence().getIdCompetence() : null;
            String competenceName = etudiant.getCompetence() != null ? etudiant.getCompetence().getNomCompetence() : null;
            Long classeId = etudiant.getClasse() != null ? etudiant.getClasse().getId() : null;
            String classeName = etudiant.getClasse() != null ? etudiant.getClasse().getNomClasse() : null;
            EtudiantDto etudiantDto = new EtudiantDto(
                    etudiant.getId(),
                    etudiant.getIdentifiant(),
                    etudiant.getCin(),
                    etudiant.getPhoto(),
                    etudiant.getNom(),
                    etudiant.getPrenom(),
                    etudiant.getEmail(),
                    etudiant.getPrivateemail(),
                    etudiant.getGender(),
                    etudiant.getDateofbirth(),
                    etudiant.getStarteducation(),
                    etudiant.isHidden(),
                    etudiant.getRole(),
                    classeId,
                    classeName,
                    competenceId,
                    competenceName
            );
            return ResponseEntity.ok(etudiantDto);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-enseignant")
    // /user/all-enseignant
    public ResponseEntity<?> getAllEnseignant() {
        try {
            List<?> enseignants = userService.getAllUserByRole(ERole.ROLE_TEACHER);
            return ResponseEntity.ok(enseignants);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all-etudiant")
    public ResponseEntity<?> getAllEtudiant() {
        try {
            List<Utilisateur> etudiants = userService.getAllUserByRole(ERole.ROLE_STUDENT);
            return ResponseEntity.ok(etudiants);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByRoleAndClass")
    public List<Utilisateur> findByRoleAndClass(@RequestParam ERole role, @RequestParam Long classId) {
        Classe classe = classeService.getClasseById(classId);
        return userService.getUtilisateursByRoleAndClasse(role, classe);
    }

    @GetMapping("/findClassByUser")
    public Classe findClassByUser(@RequestParam Long UserId) {
        return userService.getClasseByUserId(UserId);
    }

    @GetMapping("/all-enseignant2")
    public ResponseEntity<?> getAllEnseignant2() {
        try {
            List<Utilisateur> enseignants2 = userService.getAllUserByRole(ERole.ROLE_TEACHER);
            List<Map<String, Object>> transformedEnseignants = enseignants2.stream()
                    .map(enseignant2 -> {
                        Map<String, Object> transformed = new HashMap<>();
                        transformed.put("id", enseignant2.getId());
                        transformed.put("nomPrenom", enseignant2.getNom() + " " + enseignant2.getPrenom());
                        return transformed;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(transformedEnseignants);

        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/enseignant/{id}")
    // /user/all-enseignant
    public ResponseEntity<?> getEnseignant(@PathVariable Long id) {
        try {
            Utilisateur enseignant = userService.getUserByRole(id, ERole.ROLE_TEACHER);
            EtudiantDto etudiantDto = new EtudiantDto(
                    enseignant.getId(),
                    enseignant.getIdentifiant(),
                    enseignant.getCin(),
                    enseignant.getPhoto() != null ? enseignant.getPhoto() : "",
                    enseignant.getNom(),
                    enseignant.getPrenom(),
                    enseignant.getEmail(),
                    enseignant.getPrivateemail(),
                    enseignant.getGender(),
                    enseignant.getDateofbirth(),
                    enseignant.getStarteducation(),
                    enseignant.isHidden(),
                    enseignant.getRole(),
                    null,
                    null,
                    enseignant.getCompetence().getIdCompetence(),
                    enseignant.getCompetence().getNomCompetence()
            );
            return ResponseEntity.ok(etudiantDto);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/etudiant/{id}")
    public ResponseEntity<?> getEtudiant(@PathVariable Long id) {
        try {
            Utilisateur etudiant = userService.getUserByRole(id, ERole.ROLE_STUDENT);

            Long competenceId = etudiant.getCompetence() != null ? etudiant.getCompetence().getIdCompetence() : null;
            String competenceName = etudiant.getCompetence() != null ? etudiant.getCompetence().getNomCompetence() : null;

            Long classeId = etudiant.getClasse() != null ? etudiant.getClasse().getId() : null;
            String classeName = etudiant.getClasse() != null ? etudiant.getClasse().getNomClasse() : null;

            EtudiantDto etudiantDto = new EtudiantDto(
                    etudiant.getId(),
                    etudiant.getIdentifiant(),
                    etudiant.getCin(),
                    etudiant.getPhoto() != null ? etudiant.getPhoto() : "",
                    etudiant.getNom(),
                    etudiant.getPrenom(),
                    etudiant.getEmail(),
                    etudiant.getPrivateemail(),
                    etudiant.getGender(),
                    etudiant.getDateofbirth(),
                    etudiant.getStarteducation(),
                    etudiant.isHidden(),
                    etudiant.getRole(),
                    classeId,
                    classeName,
                    null,
                    null
            );
            return ResponseEntity.ok(etudiantDto);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{userId}/notes")
    public ResponseEntity<List<Note>> getNotesByUser(@PathVariable Long userId) {
        List<Note> notes = userService.getNotesByUser(userId);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/isDisabled")
    public List<Utilisateur> getDisabledUtilisateurs() {
        return userService.getDisabledUtilisateurs();
    }

    @GetMapping("/search-etudiants")
    public ResponseEntity<?> searchEtudiants(
            @RequestParam String search,
            @RequestParam String classeId
    ) {

        List<EtudiantDto> etudiants = userService.searchUsers(ERole.ROLE_STUDENT, search, classeId);
        return ResponseEntity.ok(etudiants);
    }

    @GetMapping("/search-enseignants")
    public ResponseEntity<?> searchEnseignants(
            @RequestParam String search,
            @RequestParam String classeId
    ) {

        List<EtudiantDto> etudiants = userService.searchUsers(ERole.ROLE_TEACHER, search, classeId);
        return ResponseEntity.ok(etudiants);
    }

    @PutMapping("/update/pwd")
    public ResponseEntity<?> changePassword(@RequestBody ChangePwdDto changePwd)
    {
        try {

            return new ResponseEntity<>(userService.changePassword(changePwd), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
