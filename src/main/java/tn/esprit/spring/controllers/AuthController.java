package tn.esprit.spring.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.GoogleDto;
import tn.esprit.spring.Dto.request.LoginRequest;
import tn.esprit.spring.Dto.request.RefreshRequestDTO;
import tn.esprit.spring.Dto.request.SignupRequest;
import tn.esprit.spring.Dto.response.MessageResponse;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.security.jwt.JwtUtils;
import tn.esprit.spring.services.EmailService;
import tn.esprit.spring.services.UserService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilisateurRepository userRepository;

    @Autowired
    UserService utilisateurService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService service;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(service.authenticate(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshAccessToken(@RequestBody RefreshRequestDTO request) {
        ;
        String newAccessToken = service.refreshAccessToken(request.refreshToken());
        return ResponseEntity.ok(newAccessToken);
    }

    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestBody GoogleDto dto) throws FirebaseAuthException {
        try {
        //FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(dto.getIdToken());
        String email = dto.getEmail();
        return ResponseEntity.ok(service.authenticateWithEmail(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody GoogleDto dto) {
        try {
            String email = dto.getEmail();
            Optional<Utilisateur> optionalUser = userRepository.findByEmail(email);
            // Check if the user exists
            if (!optionalUser.isPresent()) {
                throw new RuntimeException("Email not found");
            }

            // Retrieve the user
            Utilisateur user = optionalUser.get();
            user.setForgotpassword(this.generateRandomCode());
            userRepository.save(user);

            if(!user.getPrivateemail().isEmpty()){
                String subject = "Esprit Forgot Password";
                String content = String.format(
                        "Bonjour %s %s,\n\n" +
                                "Bienvenue dans Esprit !\n\n" +
                                "Voici vos Code : %s \n" +
                                "Cordialement,\n" +
                                "L'équipe Esprit",
                        user.getNom(), user.getPrenom(),user.getForgotpassword()
                );
                emailService.sendEmail(user.getPrivateemail(),subject,content);
            }
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        String newPassword = request.get("password");

        boolean success = utilisateurService.resetPassword(code, newPassword);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Password reset successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid code");
            return ResponseEntity.status(400).body(response);
        }
    }

    public String generateRandomCode() {
        int minCode = 10000;
        int maxCode = 99999;
        Random random = new Random();
        Integer Code = random.nextInt((maxCode - minCode) + 1) + minCode;
        return Code.toString();
    }

}
