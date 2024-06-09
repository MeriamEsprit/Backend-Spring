package tn.esprit.spring.Dto.emploiDuTemps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeanceClasseSummaryDto {

        private Long idSeance;
        private String heureDebut;
        private String heureFin;
        private SalleSummaryDto salle;
        private MatiereSummaryDto matiere;
        private ClasseSummaryDto classe;
        private String enseignantNomPrenom;
    }
