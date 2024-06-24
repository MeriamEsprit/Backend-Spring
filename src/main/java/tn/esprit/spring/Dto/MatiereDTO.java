package tn.esprit.spring.Dto;

import lombok.Data;

@Data
public class MatiereDTO {
    private Long id;
    private String nomMatiere;
    private Integer nbreHeures;
    private Double coefficientTP;
    private Double coefficientCC;
    private Double coefficientExamen;
    private Long moduleId;
}
