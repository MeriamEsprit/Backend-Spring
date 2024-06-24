package tn.esprit.spring.Dto;

import lombok.Data;
import tn.esprit.spring.entities.Utilisateur;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PresenceDTO {
    private Long idPresence;
    private Boolean etatPresence;
    private LocalDate datePresence;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Long utilisateurId;
    private Long justificationId;
    private Utilisateur utilisateur;
}
