package tn.esprit.spring.Dto;

import lombok.Data;
import tn.esprit.spring.entities.Matiere;

@Data
public class NoteDTO {
    private Long id;
    private Double noteCc;
    private Double noteTp;
    private Double noteExamen;
    private Double notePrincipale;
    private Double noteControle;
    private Double moyennePrincipale;
    private Double moyenneControle;
    private Long utilisateurId;
    private Long matiereId;
}
