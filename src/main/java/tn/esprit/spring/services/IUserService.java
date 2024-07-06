package tn.esprit.spring.services;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.spring.Dto.response.JwtResponse;
import tn.esprit.spring.entities.*;

import java.util.List;

public interface IUserService {
    List<Utilisateur> getAllUserByRole(ERole role);
    Classe getClasseByUserId(Long id);
    Utilisateur getUserByRole(long id , ERole role);
    Utilisateur getInfo();
    List<Utilisateur> getAllUsers();
    JwtResponse authenticate(String email, String password);
    Utilisateur getAuthenticatedUser();
    Object deleteAccountByAdmin(Long id);
    Tokens generateTokens(Utilisateur user);
    String refreshAccessToken(String refreshToken);
    List<Note> getNotesByUser(Long userId);
    List<Utilisateur> getUtilisateursByRoleAndClasse(ERole role, Classe classe);
    List<Utilisateur> getDisabledUtilisateurs();
}
