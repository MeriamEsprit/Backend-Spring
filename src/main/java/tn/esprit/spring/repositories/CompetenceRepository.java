package tn.esprit.spring.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Competence;

import javax.swing.*;
import java.util.Optional;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    Boolean existsByNomCompetence(String nomCompetence);
}
