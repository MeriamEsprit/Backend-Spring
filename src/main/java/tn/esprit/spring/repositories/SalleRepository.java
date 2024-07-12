package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Salle;

import java.util.Optional;

public interface SalleRepository extends JpaRepository<Salle, Long> {
}
