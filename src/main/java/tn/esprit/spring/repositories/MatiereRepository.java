package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Matiere;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {

}
