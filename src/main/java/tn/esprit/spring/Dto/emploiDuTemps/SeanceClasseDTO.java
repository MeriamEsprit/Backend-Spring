package tn.esprit.spring.Dto.emploiDuTemps;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Data

public class SeanceClasseDTO {
                private Instant heureDebut;
                private Instant heureFin;
                private EnseignantDTO enseignant;
                private SalleDTO salle;
                private ClasseDTO classe;
                private MatiereDTO matiere;
}


