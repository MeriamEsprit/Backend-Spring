package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Classe;


public interface ClasseRepository extends JpaRepository<Classe, Long> {
}
