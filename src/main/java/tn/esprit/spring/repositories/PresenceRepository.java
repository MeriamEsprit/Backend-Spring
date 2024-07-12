package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.spring.entities.Presence;

import java.time.LocalDate;
import java.util.Optional;


public interface PresenceRepository extends JpaRepository<Presence,Long> {
    Presence findByIdPresenceAndDatePresence(Long idPresence, LocalDate datePresence);
    Optional<Presence> findByUtilisateurIdAndDatePresence(Long utilisateurId, LocalDate datePresence);

    @Query("SELECT COUNT(p) FROM Presence p WHERE p.etatPresence = true")
    Long countByEtatPresenceTrue();

    @Query("SELECT COUNT(p) FROM Presence p WHERE p.etatPresence = false")
    Long countByEtatPresenceFalse();

    @Query("SELECT COUNT(p) FROM Presence p WHERE p.etatPresence = true AND p.utilisateur.classe.id = :classeId")
    Long countByEtatPresenceTrueAndClasseId(Long classeId);

    @Query("SELECT COUNT(p) FROM Presence p WHERE p.etatPresence = false AND p.utilisateur.classe.id = :classeId")
    Long countByEtatPresenceFalseAndClasseId(Long classeId);

    @Query("SELECT COUNT(p) FROM Presence p WHERE p.etatPresence = true AND p.matiere.id = :matiereId")
    Long countByEtatPresenceTrueAndMatiereId(Long matiereId);

    @Query("SELECT COUNT(p) FROM Presence p WHERE p.etatPresence = false AND p.matiere.id = :matiereId")
    Long countByEtatPresenceFalseAndMatiereId(Long matiereId);

}
