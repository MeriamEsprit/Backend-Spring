package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;

import java.util.List;
import java.util.Optional;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    boolean existsByNomMatiere(String nomMatiere);
    Optional<Matiere> findByNomMatiere(String nomMatiere);
    List<Matiere> findAllByModule(Module module);

    @Query("SELECT m FROM Matiere m JOIN FETCH m.module WHERE m.id = :id")
    Optional<Matiere> findByIdWithModule(@Param("id") Long id);
}
