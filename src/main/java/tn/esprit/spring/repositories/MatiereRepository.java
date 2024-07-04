package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Matiere;

import java.util.Optional;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    Optional<Matiere> findByNomMatiere(String nomMatiere);

}
