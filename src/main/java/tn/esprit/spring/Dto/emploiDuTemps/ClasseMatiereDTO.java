package tn.esprit.spring.Dto.emploiDuTemps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class ClasseMatiereDTO {
        private Long idClasse;
        private List<Long> idMatieres;
}
