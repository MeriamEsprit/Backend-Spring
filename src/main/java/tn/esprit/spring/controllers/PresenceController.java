package tn.esprit.spring.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.MatiereDTO;
import tn.esprit.spring.Dto.PresenceDTO;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.JustificationRepository;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.services.IPresenceService;
import tn.esprit.spring.services.MatiereServicesImp;
import tn.esprit.spring.services.PresenceServiceImpl;
import tn.esprit.spring.services.UserService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/presences")
@RequiredArgsConstructor
public class PresenceController {

    private final IPresenceService presenceService;
    private final UtilisateurRepository utilisateurRepository;
    private final JustificationRepository justificationRepository;
    private final MatiereRepository matiereRepository;

    @Autowired
    UserService userService;
    @Autowired
    MatiereServicesImp matiereServices;

    @PostMapping("/addPresence")
    public PresenceDTO addPresence(@RequestBody PresenceDTO presenceDTO) {
        Presence presence = PresenceDTO.mapToEntity(presenceDTO, utilisateurRepository, justificationRepository,matiereRepository);
        Presence savedPresence = presenceService.addPresence(presence);
        return PresenceDTO.mapToDTO(savedPresence);
    }


    @GetMapping("/getAllPresence")
    public List<PresenceDTO> findAllPresences() {
        List<Presence> presences = presenceService.getAllPresences();
        return presences.stream().map(PresenceDTO::mapToDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/deletePresence/{id}")
    public void deletePresence(@PathVariable Long id) {
        presenceService.deletePresence(id);
    }

    @PostMapping(value = "/addPresenceForEtudiant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PresenceDTO> addPresenceForEtudiant(@RequestParam Long studentId, @RequestBody PresenceDTO presenceDTO) {
        System.out.println("Received PresenceDTO: " + presenceDTO);
        Presence presenceDetails = PresenceDTO.mapToEntity(presenceDTO, utilisateurRepository, justificationRepository, matiereRepository);
        System.out.println("Mapped Presence Entity: " + presenceDetails);
        Presence savedPresence = presenceService.addPresenceForEtudiant(studentId, presenceDetails);
        return ResponseEntity.ok(PresenceDTO.mapToDTO(savedPresence));
    }

    @PutMapping("/updatePresence")
    public PresenceDTO updatePresenceForEtudiant(@RequestParam Long studentId, @RequestBody PresenceDTO presenceDTO) {
        Presence presence = PresenceDTO.mapToEntity(presenceDTO, utilisateurRepository, justificationRepository, matiereRepository);
        Presence updatedPresence = presenceService.updatePresenceForEtudiant(studentId,presence);
        return PresenceDTO.mapToDTO(updatedPresence);
    }

    @GetMapping("/listutilisateursbyclasse/{classId}")
    public List<Utilisateur> findStudentByClasse(@PathVariable Long classId) {
        return presenceService.findStudentByClasse(classId);
    }

    @GetMapping("/utilisateur/{userId}/month/{month}/year/{year}")
    public Map<String, List<PresenceDTO>> getMonthlyAttendance(@PathVariable Long userId, @PathVariable int month, @PathVariable int year) {
        Map<String, List<Presence>> monthlyAttendance = presenceService.getMonthlyAttendance(userId, month, year);
        return monthlyAttendance.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(PresenceDTO::mapToDTO).collect(Collectors.toList())
                ));
    }

    @GetMapping("/classe/{classeId}")
    public List<PresenceDTO> getPresencesByClasseId(@PathVariable Long classeId) {
        List<Presence> presences = presenceService.getPresencesByClasseId(classeId);
        return presences.stream().map(PresenceDTO::mapToDTO).collect(Collectors.toList());
    }

    @GetMapping("/getPresenceById/{id}")
    public PresenceDTO getPresenceById(@PathVariable Long id) {
        Presence presence = presenceService.getPresenceById(id);
        return PresenceDTO.mapToDTO(presence);
    }

    @GetMapping("/getPresenceByIdAndDate/{id}/{date}")
    public PresenceDTO getPresenceByIdAndDate(@PathVariable Long id, @PathVariable LocalDate date) {
        Presence presence = presenceService.getPresenceByIdAndDate(id, date);
        return PresenceDTO.mapToDTO(presence);
    }

    @GetMapping("/getAllPresencesByUserId/{userId}")
    public List<PresenceDTO> getAllPresencesByUserId(@PathVariable Long userId) {
        List<Presence> presences = presenceService.getAllPresencesByUserId(userId);
        return presences.stream().map(PresenceDTO::mapToDTO).collect(Collectors.toList());
    }

    @GetMapping("/checkPresenceExists")
    public ResponseEntity<Boolean> checkPresenceExists(@RequestParam Long studentId, @RequestParam String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            System.out.println("Checking presence for studentId: " + studentId + " on date: " + date);
            boolean exists = presenceService.checkPresenceExists(studentId, localDate);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @GetMapping("/dashboard/presenceByMatiere/{idMatiere}")
    public ArrayList<Double> dashboardPresence (@PathVariable Long idMatiere) {
        Utilisateur user = userService.getInfo();
        MatiereDTO matiere = new MatiereDTO();


        List<Presence> presences = presenceService.getAllPresencesByUserId(user.getId());
        long hoursListPresence = 0;
        long hoursListAbsence = 0;
        long hoursMatiere = 0;
        try {
            matiere =  matiereServices.getMatiereById(idMatiere);
            hoursMatiere = matiere.getNbreHeures();
            for (Presence p : presences) {
                LocalTime heureDebut = p.getHeureDebut();
                LocalTime heureFin = p.getHeureFin();
                long hours = Duration.between(heureDebut, heureFin).toHours();
                if(matiere.getId().equals(p.getMatiere().getId())){
                    if (p.getEtatPresence()==true){
                        hoursListPresence += hours;
                    }else{
                        hoursListAbsence+=hours;
                    }
                }

            }
        }catch (Exception e){
            for (Presence p : presences) {
                LocalTime heureDebut = p.getHeureDebut();
                LocalTime heureFin = p.getHeureFin();
                long hours = Duration.between(heureDebut, heureFin).toHours();
                if (p.getEtatPresence()==true){
                    hoursListPresence += hours;
                }else{
                    hoursListAbsence+=hours;
                }
            }
            for (MatiereDTO m:matiereServices.getAllMatieres()) {
                hoursMatiere += m.getNbreHeures();
            }
        }

        ArrayList<Double> hours = new ArrayList<>();
        hours.add((double)hoursListPresence / hoursMatiere * 100);
        hours.add((double)hoursListAbsence / hoursMatiere * 100);
        hours.add((double)hoursMatiere);
        return hours;
    }

}
