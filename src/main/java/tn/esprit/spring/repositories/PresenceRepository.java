package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Presence;

import java.time.LocalDate;


public interface PresenceRepository extends JpaRepository<Presence,Long> {
    Presence findByIdPresenceAndDatePresence(Long idPresence, LocalDate datePresence);

}
