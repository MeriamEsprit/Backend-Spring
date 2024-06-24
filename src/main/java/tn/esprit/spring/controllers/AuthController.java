package tn.esprit.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.request.LoginRequest;
import tn.esprit.spring.Dto.request.RefreshRequestDTO;
import tn.esprit.spring.Dto.request.SignupRequest;
import tn.esprit.spring.Dto.response.MessageResponse;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.RoleRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.security.jwt.JwtUtils;
import tn.esprit.spring.services.UserService;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilisateurRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService service;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(service.authenticate(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        try {
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Email déjà utilisé!"));
            }

            // Create new user's account
            Utilisateur user = new Utilisateur(signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()));
            // Always assign either user role / employe

            if (signUpRequest.getRole().equals("TEACHER")) {
                user.setRole(ERole.ROLE_TEACHER);
            } else if (signUpRequest.getRole().equals("STUDENT")){
                user.setRole(ERole.ROLE_STUDENT);
            }

            userRepository.save(user);

            return ResponseEntity.ok(service.authenticate(signUpRequest.getEmail(), signUpRequest.getPassword()));
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshAccessToken(@RequestBody RefreshRequestDTO request) {
        ;
        String newAccessToken = service.refreshAccessToken(request.refreshToken());
        return ResponseEntity.ok(newAccessToken);
    }

}
