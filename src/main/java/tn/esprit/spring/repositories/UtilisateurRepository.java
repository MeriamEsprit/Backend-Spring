package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.Dto.response.EtudiantDto;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Utilisateur;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByPrivateemail(String email);

    Optional<Utilisateur> findByForgotpassword(String forgotpassword);

    List<Utilisateur> findAllByRole(ERole role);

    Utilisateur findUtilisateurByIdAndRole(Long id , ERole role);
    
    Utilisateur getUtilisateurByEmail(String email);
    Boolean existsByEmail(String email);


    @Query("SELECT u FROM Utilisateur u WHERE u.role = :role AND u.classe.id = :classeId")
    List<Utilisateur> findByRoleAndClasse(@Param("role") ERole role, @Param("classe") Classe classe);

    @Query("SELECT u.classe FROM Utilisateur u WHERE u.id = :utilisateurId")
    Classe findClasseByUtilisateurId(@Param("utilisateurId") Long utilisateurId);


//    List<Utilisateur> findAllByRolesContainingAndIsHiddenFalse(Set<Role> roles);

    List<Utilisateur> findByClasseId(Long classeId);


}
