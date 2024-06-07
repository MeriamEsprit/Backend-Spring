package tn.esprit.spring.services;

import tn.esprit.spring.entities.SeanceClasse;

import java.util.List;

public interface ISeanceclasseServices {
    SeanceClasse saveSeanceclasse(SeanceClasse seanceclasse);
    SeanceClasse updateSeanceclasse(SeanceClasse seanceclasse);
    void deleteSeanceclasse(Long id);
    SeanceClasse getSeanceclasseById(Long id);
    List<SeanceClasse> getAllSeanceclasses();
}
