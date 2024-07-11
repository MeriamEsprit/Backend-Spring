package tn.esprit.spring.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Utilisateur;

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
    private String gender;
    private String dateofbirth;
    private String starteducation;
    private boolean isHidden;
    private ERole role;
    private Long classeId;
    private String classeName;
    private Long competenceId;
    private String competenceName;
<<<<<<< HEAD
=======

    public static EtudiantDto convertToDto(Utilisateur utilisateur) {
        return new EtudiantDto(
                utilisateur.getId(),
                utilisateur.getIdentifiant(),
                utilisateur.getCin(),
                utilisateur.getPhoto(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getPrivateemail(),
                utilisateur.getGender(),
                utilisateur.getDateofbirth(),
                utilisateur.getStarteducation(),
                utilisateur.isHidden(),
                utilisateur.getRole(),
                utilisateur.getClasse() != null ? utilisateur.getClasse().getId() : null,
                utilisateur.getClasse() != null ? utilisateur.getClasse().getNomClasse() : null,
                utilisateur.getCompetence() != null ? utilisateur.getCompetence().getIdCompetence() : null,
                utilisateur.getCompetence() != null ? utilisateur.getCompetence().getNomCompetence() : null
        );
    }
>>>>>>> 0263fbc95d836694b770c83a9bd3175a22f122c4
}
