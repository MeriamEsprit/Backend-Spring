package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Emploidutemp;

public interface EmploiRepository extends JpaRepository<Emploidutemp, Long> {
}
