package tn.esprit.spring.Dto.emploiDuTemps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class MatiereDTO {
    private Long idMatiere;
    public MatiereDTO(Long idMatiere) {
        this.idMatiere = idMatiere;
    }


}
