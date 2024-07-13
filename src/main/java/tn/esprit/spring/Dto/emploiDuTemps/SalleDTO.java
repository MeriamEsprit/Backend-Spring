package tn.esprit.spring.Dto.emploiDuTemps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class SalleDTO {
    private Long idSalle;
    public SalleDTO(Long idSalle) {
        this.idSalle = idSalle;
    }
    }
