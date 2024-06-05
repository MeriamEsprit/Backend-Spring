package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Competence;

import javax.swing.*;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {

}
