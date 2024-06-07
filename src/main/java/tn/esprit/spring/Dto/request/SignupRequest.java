package tn.esprit.spring.Dto.request;


import lombok.Data;

@Data
public class SignupRequest {


    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
    private String password;
    private String role;

}
