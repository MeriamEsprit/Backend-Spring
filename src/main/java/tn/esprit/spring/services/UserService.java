package tn.esprit.spring.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Dto.response.JwtResponse;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.RefreshTokenRepository;
import tn.esprit.spring.repositories.RoleRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.security.jwt.JwtUtils;
import tn.esprit.spring.security.services.UserDetailsServiceImpl;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements IUserService {

    UtilisateurRepository userRepository;
    UserDetailsServiceImpl userDetailsService;
    RoleRepository roleRepository;
    RefreshTokenRepository refreshTokenRepository;
    JwtUtils jwtUtils;
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;


    public List<Utilisateur> getAllUsers() {
        return userRepository.findAll();
    }


//    public List<Utilisateur> getAllUsersByRoles(ERole role) {
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Role non trouvé!")));
//        return userRepository.findAllByRolesContainingAndIsHiddenFalse(roles);
//    }


    public Utilisateur getClientById(Long id) {
        return userRepository.findById(id).get();
    }

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

    public Utilisateur getAuthenticatedUser() {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur user = this.userRepository.findByEmail(authenticatedUserEmail).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return user;

    }



    public Object deleteAccountByAdmin(Long id) {
        Utilisateur u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        userRepository.delete(u);
        return u;
    }


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

}
