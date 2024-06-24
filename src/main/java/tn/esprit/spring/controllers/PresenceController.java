package tn.esprit.spring.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.PresenceDTO;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.JustificationRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.services.IPresenceService;

import java.time.LocalDate;
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

    @PostMapping("/addPresence")
    public PresenceDTO addPresence(@RequestBody PresenceDTO presenceDTO) {
        Presence presence = mapToEntity(presenceDTO);
        Presence savedPresence = presenceService.addPresence(presence);
        return mapToDTO(savedPresence);
    }

    @GetMapping("/getAllPresence")
    public List<PresenceDTO> findAllPresences() {
        List<Presence> presences = presenceService.getAllPresences();
        return presences.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @PutMapping("/updatePresence")
    public PresenceDTO updatePresence(@RequestBody PresenceDTO presenceDTO) {
        Presence presence = mapToEntity(presenceDTO);
        Presence updatedPresence = presenceService.updatePresence(presence);
        return mapToDTO(updatedPresence);
    }

    @DeleteMapping("/deletePresence/{id}")
    public void deletePresence(@PathVariable Long id) {
        presenceService.deletePresence(id);
    }

    @PostMapping("/addPresenceForEtudiant")
    public PresenceDTO addPresenceForEtudiant(@RequestParam Long studentId, @RequestBody PresenceDTO presenceDTO) {
        Presence presenceDetails = mapToEntity(presenceDTO);
        Presence savedPresence = presenceService.addPresenceForEtudiant(studentId, presenceDetails);
        return mapToDTO(savedPresence);
    }
    @GetMapping("/listutilisateursbyclasse/{idClass}")
    public List<Utilisateur>findStudentByClasse(@PathVariable Long classId)
    {
        return presenceService.findStudentByClasse(classId);
    }

    @GetMapping("/utilisateur/{userId}/month/{month}/year/{year}")
    public Map<String, List<PresenceDTO>> getMonthlyAttendance(@PathVariable Long userId, @PathVariable int month, @PathVariable int year) {
        Map<String, List<Presence>> monthlyAttendance = presenceService.getMonthlyAttendance(userId, month, year);
        return monthlyAttendance.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(this::mapToDTO).collect(Collectors.toList())
                ));
    }

    @GetMapping("/classe/{classeId}")
    public List<PresenceDTO> getPresencesByClasseId(@PathVariable Long classeId) {
        List<Presence> presences = presenceService.getPresencesByClasseId(classeId);
        return presences.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    @GetMapping("/getPresenceById/{id}")
    public PresenceDTO getPresenceById(@PathVariable Long id) {
        Presence presence = presenceService.getPresenceById(id);
        return mapToDTO(presence);
    }

    @GetMapping("/getPresenceByIdAndDate/{id}/{date}")
    public PresenceDTO getPresenceByIdAndDate(@PathVariable Long id, @PathVariable LocalDate date) {
        Presence presence = presenceService.getPresenceByIdAndDate(id, date);
        return mapToDTO(presence);
    }

    @GetMapping("/getAllPresencesByUserId/{userId}")
    public List<PresenceDTO> getAllPresencesByUserId(@PathVariable Long userId) {
        List<Presence> presences = presenceService.getAllPresencesByUserId(userId);
        return presences.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private PresenceDTO mapToDTO(Presence presence) {
        PresenceDTO presenceDTO = new PresenceDTO();
        presenceDTO.setIdPresence(presence.getIdPresence());
        presenceDTO.setEtatPresence(presence.getEtatPresence());
        presenceDTO.setDatePresence(presence.getDatePresence());
        presenceDTO.setHeureDebut(presence.getHeureDebut());
        presenceDTO.setHeureFin(presence.getHeureFin());
        presenceDTO.setUtilisateurId(presence.getUtilisateur() != null ? presence.getUtilisateur().getId() : null);
        presenceDTO.setJustificationId(presence.getJustification() != null ? presence.getJustification().getIdJustification() : null);
        presenceDTO.setUtilisateur(presence.getUtilisateur());
        return presenceDTO;
    }

    private Presence mapToEntity(PresenceDTO presenceDTO) {
        Presence presence = new Presence();
        presence.setIdPresence(presenceDTO.getIdPresence());
        presence.setEtatPresence(presenceDTO.getEtatPresence());
        presence.setDatePresence(presenceDTO.getDatePresence());
        presence.setHeureDebut(presenceDTO.getHeureDebut());
        presence.setHeureFin(presenceDTO.getHeureFin());
        if (presenceDTO.getUtilisateurId() != null) {
            presence.setUtilisateur(utilisateurRepository.findById(presenceDTO.getUtilisateurId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + presenceDTO.getUtilisateurId())));
        }
        if (presenceDTO.getJustificationId() != null) {
            presence.setJustification(justificationRepository.findById(presenceDTO.getJustificationId())
                    .orElseThrow(() -> new EntityNotFoundException("Justification not found with id " + presenceDTO.getJustificationId())));
        }
        return presence;
    }
}
