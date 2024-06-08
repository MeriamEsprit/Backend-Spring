package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;

import java.util.List;

@Repository
public interface PresenceRepository extends JpaRepository<Presence,Long> {
    @Query("SELECT u FROM Utilisateur u WHERE u.id = :userId AND u.role = 'ROLE_ETUDIANT'")
    Utilisateur findEtudiantById(@Param("userId") Long userId);

}
