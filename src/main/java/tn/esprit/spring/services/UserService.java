package tn.esprit.spring.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.spring.Dto.request.ChangePwdDto;
import tn.esprit.spring.Dto.response.JwtResponse;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.NoteRepository;
import tn.esprit.spring.repositories.RefreshTokenRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.security.jwt.JwtUtils;
import tn.esprit.spring.security.services.UserDetailsServiceImpl;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements IUserService {
    private final NoteRepository noteRepository;

    UtilisateurRepository userRepository;
    UserDetailsServiceImpl userDetailsService;
    RefreshTokenRepository refreshTokenRepository;
    JwtUtils jwtUtils;
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;

    @Override
    public List<Utilisateur> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public JwtResponse authenticate(String email, String password) {
        try {
            // Retrieve the user by username
            Optional<Utilisateur> optionalUser = userRepository.findByEmail(email);

            // Check if the user exists
            if (!optionalUser.isPresent()) {
                throw new RuntimeException("Email not found");
            }

            // Retrieve the user
            Utilisateur user = optionalUser.get();

            // Verify the password using the password encoder
            if (!passwordEncoder.matches(password, user.getMotDePasse())) {
                throw new RuntimeException("Invalid credentials");
            }

            Tokens tokens = generateTokens(user);
            JwtResponse jwtResponse = new JwtResponse().builder()
                    .id(user.getId())
                    .type("Bearer")
                    .token(tokens.accessToken())
                    .refreshToken(tokens.refreshToken())
                    .email(user.getEmail())
                    .build();
            return jwtResponse;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean resetPassword(String code, String newPassword) {
        Optional<Utilisateur> userOptional = userRepository.findByForgotpassword(code);
        if (userOptional.isPresent()) {
            Utilisateur user = userOptional.get();
            user.setMotDePasse(passwordEncoder.encode(newPassword));
            user.setForgotpassword(null);  // Optionally clear the code after use
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Utilisateur getAuthenticatedUser() {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur user = this.userRepository.findByEmail(authenticatedUserEmail).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return user;

    }


    @Override
    public Object deleteAccountByAdmin(Long id) {
        Utilisateur u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        userRepository.delete(u);
        return u;
    }

    @Override
    public Tokens generateTokens(Utilisateur user) {
        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        // Save the refresh token in the database
        RefreshToken refreshTokenEntity = new RefreshToken();

//        refreshTokenEntity.setId(user.getId());
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7)); // Set token expiration
        refreshTokenRepository.save(refreshTokenEntity);

        return new Tokens(accessToken, refreshToken);
    }

    @Override
    public Utilisateur getInfo() {
        return getAuthenticatedUser();
    }
    @Override
    public String refreshAccessToken(String refreshToken) {
        RefreshToken tokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // Check if the refresh token has expired
        if (tokenEntity.getExpiryDate().isAfter(LocalDateTime.now())) {
            Utilisateur user = tokenEntity.getUtilisateur();
            return jwtUtils.generateRefreshToken(user);
        } else {
            throw new RuntimeException("Refresh token has expired");
        }
    }
    @Override
    public List<Utilisateur> getAllUserByRole(ERole role) {

        return userRepository.findAllByRole(role);
    }
    @Override
    public List<Note> getNotesByUser(Long userId) {
        return noteRepository.findByUtilisateurId(userId);
    }

    @Override
    public List<Utilisateur> getUtilisateursByRoleAndClasse(ERole role, Classe classe) {
        return userRepository.findByRoleAndClasse(role, classe);
    }

    @Override
    public Classe getClasseByUserId(Long id) {
        return userRepository.findClasseByUtilisateurId(id);
    }

    @Override
    public Utilisateur getUserByRole(long id , ERole role) {
        return userRepository.findUtilisateurByIdAndRole(id,role);
    }

    @Override
    public Object changePassword(ChangePwdDto changePwd) {
        Utilisateur user = getAuthenticatedUser();
        if (passwordEncoder.matches(changePwd.getOldPwd(), user.getMotDePasse())) {
            user.setMotDePasse(passwordEncoder.encode(changePwd.getNewPwd()));
            userRepository.save(user);
            return "Password changed successfully.";
        } else {
            return "Old password is incorrect.";
        }
    }

    public JwtResponse authenticateWithEmail(String email) {
        try {
            // Retrieve the user by username
            Optional<Utilisateur> optionalUser = userRepository.findByPrivateemail(email);

            // Check if the user exists
            if (!optionalUser.isPresent()) {
                throw new RuntimeException("Email not found");
            }

            // Retrieve the user
            Utilisateur user = optionalUser.get();

            // Verify the password using the password encoder

            Tokens tokens = generateTokens(user);
            JwtResponse jwtResponse = new JwtResponse().builder()
                    .id(user.getId())
                    .type("Bearer")
                    .token(tokens.accessToken())
                    .refreshToken(tokens.refreshToken())
                    .email(user.getEmail())
                    .build();
            return jwtResponse;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }


}
