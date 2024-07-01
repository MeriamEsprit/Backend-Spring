package tn.esprit.spring.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DtoReclamation {
    private Long id;
    private String description;
    private String subject;
    private String statut;
    private LocalDate date;
    private Long utilisateurId;
}
