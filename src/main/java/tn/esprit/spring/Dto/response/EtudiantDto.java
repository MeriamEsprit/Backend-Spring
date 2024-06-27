package tn.esprit.spring.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.ERole;

@Getter
@Setter
@AllArgsConstructor
public class EtudiantDto {
    private Long id;
    private String identifiant;
    private String cin;
    private String photo;
    private String nom;
    private String prenom;
    private String email;
    private String privateemail;
    private boolean isHidden;
    private ERole role;
    private Long classeId;
    private String classeName;
}
