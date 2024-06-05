package tn.esprit.spring.services;

import tn.esprit.spring.entities.Competence;

import java.util.List;

public interface ICompetenceServices {
    Competence saveCompetence(Competence competence);
    Competence updateCompetence(Competence competence);
    void deleteCompetence(Long id);
    Competence getCompetenceById(Long id);
    List<Competence> getAllCompetences();
}
