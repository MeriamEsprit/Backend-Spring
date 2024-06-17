package tn.esprit.spring.services;

import tn.esprit.spring.entities.Matiere;

import java.util.List;

public interface IMatiereServices {
    Matiere saveMatiere(Matiere matiere,Long moduleId);
    Matiere updateMatiere(Long id, Matiere matiereDetails, Long moduleId);
    void deleteMatiere(Long id);
    Matiere getMatiereById(Long id);
    List<Matiere> getAllMatieres();
}
