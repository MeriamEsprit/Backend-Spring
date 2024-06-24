package tn.esprit.spring.controllers;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.response.EtudiantDto;
import tn.esprit.spring.Dto.request.SignupRequest;
import tn.esprit.spring.Dto.response.MessageResponse;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
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
import tn.esprit.spring.services.UserService;

import java.util.List;


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
            if (signUpRequest.getRole().equals("TEACHER")) {
                user.setRole(ERole.ROLE_TEACHER);
            } else if (signUpRequest.getRole().equals("STUDENT")){
                user.setRole(ERole.ROLE_STUDENT);
            }
            userRepository.save(user);

            user.setIdentifiant(generateIdentifiant(user.getId(),user.getRole()));
            user.setMotDePasse(encoder.encode(user.getIdentifiant()));
            userRepository.save(user);

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

            if(!signUpRequest.getCin().isEmpty())user.setCin(signUpRequest.getCin());
            if(!signUpRequest.getNom().isEmpty())user.setNom(signUpRequest.getNom());
            if(!signUpRequest.getPrenom().isEmpty())user.setPrenom(signUpRequest.getPrenom());
            if(!signUpRequest.getEmail().isEmpty())user.setEmail(signUpRequest.getEmail());

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

            userRepository.save(user);

            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generateIdentifiant(Long userId,ERole role) {
        int year = LocalDate.now().getYear() % 1000;
        if(role == ERole.ROLE_STUDENT ){
            return String.format("%dSMT%03d", year, userId);
        }
        if(role == ERole.ROLE_TEACHER ){
            return String.format("%dTMT%03d", year, userId);
        }
        return "0000";
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
    // /user/all-etudiant
    public ResponseEntity<?> getAllEtudiant() {
        try {
            List<?> enseignants = userService.getAllUserByRole(ERole.ROLE_STUDENT);
            return ResponseEntity.ok(enseignants);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/enseignant/{id}")
    // /user/all-enseignant
    public ResponseEntity<?> getEnseignant(@PathVariable Long id) {
        try {
            Utilisateur enseignant = userService.getUserByRole(id, ERole.ROLE_TEACHER);
            return ResponseEntity.ok(enseignant);
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
            Long classeId = etudiant.getClasse() != null ? etudiant.getClasse().getId() : null;
            EtudiantDto etudiantDto = new EtudiantDto(
                    etudiant.getId(), etudiant.getIdentifiant(), etudiant.getCin(), etudiant.getNom(),
                    etudiant.getPrenom(), etudiant.getEmail(), etudiant.isHidden(), etudiant.getRole(), classeId);
            return ResponseEntity.ok(etudiantDto);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
