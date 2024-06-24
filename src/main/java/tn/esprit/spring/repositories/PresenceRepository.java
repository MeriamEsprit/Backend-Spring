package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Presence;


public interface PresenceRepository extends JpaRepository<Presence,Long> {


}
