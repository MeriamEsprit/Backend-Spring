package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;

import java.util.List;
import java.util.Optional;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    boolean existsByNomMatiere(String nomMatiere);
    Optional<Matiere> findByNomMatiere(String nomMatiere);
    List<Matiere> findAllByModule(Module module);

}
