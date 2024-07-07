package tn.esprit.spring.Dto;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.JustificationRepository;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;

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
    private Long idmatiere;
    private String nomMatiere;
    private Integer nbreHeures;


        public static PresenceDTO mapToDTO(Presence presence) {
        PresenceDTO presenceDTO = new PresenceDTO();
        presenceDTO.setIdPresence(presence.getIdPresence());
        presenceDTO.setEtatPresence(presence.getEtatPresence());
        presenceDTO.setDatePresence(presence.getDatePresence());
        presenceDTO.setHeureDebut(presence.getHeureDebut());
        presenceDTO.setHeureFin(presence.getHeureFin());
        presenceDTO.setUtilisateurId(presence.getUtilisateur() != null ? presence.getUtilisateur().getId() : null);
        presenceDTO.setJustificationId(presence.getJustification() != null ? presence.getJustification().getIdJustification() : null);
        presenceDTO.setIdmatiere(presence.getMatiere() != null ? presence.getMatiere().getId() : null);
        presenceDTO.setNomMatiere(presence.getMatiere() != null ? presence.getMatiere().getNomMatiere() : null);
        presenceDTO.setNbreHeures(presence.getMatiere() != null ? presence.getMatiere().getNbreHeures() : 0);
        return presenceDTO;
    }
    public static Presence mapToEntity(PresenceDTO presenceDTO, UtilisateurRepository utilisateurRepository, JustificationRepository justificationRepository, MatiereRepository matiereRepository) {
        Presence presence = new Presence();
        presence.setIdPresence(presenceDTO.getIdPresence());
        presence.setEtatPresence(presenceDTO.getEtatPresence());
        presence.setDatePresence(presenceDTO.getDatePresence());
        presence.setHeureDebut(presenceDTO.getHeureDebut());
        presence.setHeureFin(presenceDTO.getHeureFin());

        if (presenceDTO.getUtilisateurId() != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(presenceDTO.getUtilisateurId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + presenceDTO.getUtilisateurId()));
            presence.setUtilisateur(utilisateur);
        }

        if (presenceDTO.getJustificationId() != null) {
            presence.setJustification(justificationRepository.findById(presenceDTO.getJustificationId())
                    .orElseThrow(() -> new EntityNotFoundException("Justification not found with id " + presenceDTO.getJustificationId())));
        }

        if (presenceDTO.getIdmatiere() != null) {
            Matiere matiere = matiereRepository.findById(presenceDTO.getIdmatiere())
                    .orElseThrow(() -> new EntityNotFoundException("Matière not found with id " + presenceDTO.getIdmatiere()));
            presence.setMatiere(matiere);
        } else if (presenceDTO.getNomMatiere() != null) {
            Matiere matiere = matiereRepository.findByNomMatiere(presenceDTO.getNomMatiere())
                    .orElseThrow(() -> new EntityNotFoundException("Matière not found with name " + presenceDTO.getNomMatiere()));
            presence.setMatiere(matiere);
        }

        return presence;
    }
}


