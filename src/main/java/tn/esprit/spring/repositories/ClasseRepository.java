package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Utilisateur;

import java.util.List;


public interface ClasseRepository extends JpaRepository<Classe, Long> {

}
