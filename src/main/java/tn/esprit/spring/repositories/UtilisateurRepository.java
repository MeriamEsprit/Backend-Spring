package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Utilisateur;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findAllByRole(ERole role);

    Boolean existsByEmail(String email);
//    List<Utilisateur> findAllByRolesContainingAndIsHiddenFalse(Set<Role> roles);
}
