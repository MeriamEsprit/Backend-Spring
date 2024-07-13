package tn.esprit.spring.Dto.emploiDuTemps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class ClasseDTO {
    private Long idClasse;
    private String nom;
    public ClasseDTO(Long idClasse) {
        this.idClasse = idClasse;
    }
}
