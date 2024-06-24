package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.spring.entities.SeanceClasse;

import java.util.List;

public interface SeanceclasseRepository extends JpaRepository<SeanceClasse, Long> {

    @Query("SELECT s FROM SeanceClasse s WHERE s.enseignant.id = :idEnseignant")
    List<SeanceClasse> findByEnseignantId(Long idEnseignant);

    @Query("SELECT s FROM SeanceClasse s WHERE s.classe.id = :idClasse")
    List<SeanceClasse> findByClasseId(Long idClasse);
}
