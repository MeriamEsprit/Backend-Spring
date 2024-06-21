package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Justification;

public interface JustificationRepository extends JpaRepository<Justification, Long> {
}
