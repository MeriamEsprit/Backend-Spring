package tn.esprit.spring.Dto.request;


import lombok.Data;

@Data
public class SignupRequest {

    private String nom;
    private String prenom;
    private String email;
    private String privateemail;
    private String gender;
    private String dateofbirth;
    private String starteducation;
    private String Photo;
    private String cin;
    private String classe;
    private String competence;
    private String role;

}
