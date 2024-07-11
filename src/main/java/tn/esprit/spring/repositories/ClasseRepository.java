package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.spring.entities.Classe;

import java.util.List;


public interface ClasseRepository extends JpaRepository<Classe, Long> {
    public boolean existsBynomClasse(String nomClasse);
    @Query("SELECT c FROM Classe c JOIN FETCH c.matieres")
    List<Classe> findAllWithMatieres();
}
