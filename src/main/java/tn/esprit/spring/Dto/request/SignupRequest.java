package tn.esprit.spring.Dto.request;


import lombok.Data;

@Data
public class SignupRequest {


    private String nom;
    private String prenom;
    private String email;
    private String cin;
    private String classe;
    private String role;

}