package tn.esprit.spring.Dto;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import tn.esprit.spring.entities.Justification;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.PresenceRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JustificationDTO {
    private Long idJustification;
    private String name;
    private String reason;
    private int status;
    private Date submissionDate;
    private Date validationDate;
    private String filePath;
    private List<Long> presenceIds;
    private Long userId;
    private String studentNom;
    private String studentPrenom;
    private String studentClasse;
    private LocalDate presenceDate;

    public static JustificationDTO mapToDTO(Justification justification) {
        JustificationDTO justificationDTO = new JustificationDTO();
        justificationDTO.setIdJustification(justification.getIdJustification());
        justificationDTO.setName(justification.getName());
        justificationDTO.setReason(justification.getReason());
        justificationDTO.setStatus(justification.getStatus());
        justificationDTO.setSubmissionDate(justification.getSubmissionDate());
        justificationDTO.setValidationDate(justification.getValidationDate());
        justificationDTO.setFilePath(justification.getFilePath());
        justificationDTO.setPresenceIds(justification.getPresences().stream().map(Presence::getIdPresence).collect(Collectors.toList()));
        justificationDTO.setPresenceDate(justification.getPresences().isEmpty() ? null : justification.getPresences().get(0).getDatePresence());
        if (!justification.getPresences().isEmpty()) {
            Presence presence = justification.getPresences().get(0);
            Utilisateur student = presence.getUtilisateur();
            if (student != null) {
                justificationDTO.setStudentNom(student.getNom());
                justificationDTO.setStudentPrenom(student.getPrenom());
                justificationDTO.setStudentClasse(student.getClasse().getNomClasse());
                justificationDTO.setUserId(student.getId());
            }
        }
        return justificationDTO;
    }

    public static Justification mapToEntity(JustificationDTO justificationDTO, PresenceRepository presenceRepository) {
        Justification justification = new Justification();
        justification.setIdJustification(justificationDTO.getIdJustification());
        justification.setName(justificationDTO.getName());
        justification.setReason(justificationDTO.getReason());
        justification.setStatus(justificationDTO.getStatus());
        justification.setSubmissionDate(justificationDTO.getSubmissionDate());
        justification.setValidationDate(justificationDTO.getValidationDate());
        justification.setFilePath(justificationDTO.getFilePath());
        List<Presence> presences = justificationDTO.getPresenceIds().stream()
                .map(id -> presenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Presence not found with id " + id)))
                .collect(Collectors.toList());
        justification.setPresences(presences);
        return justification;
    }
}
