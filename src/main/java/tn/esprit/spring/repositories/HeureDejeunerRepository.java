package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.HeureDejeuner;

public interface HeureDejeunerRepository extends JpaRepository<HeureDejeuner, Long> {
}

