package tn.esprit.spring.services;

import tn.esprit.spring.entities.Seanceclasse;

import java.util.List;

public interface ISeanceclasseServices {
    Seanceclasse saveSeanceclasse(Seanceclasse seanceclasse);
    Seanceclasse updateSeanceclasse(Seanceclasse seanceclasse);
    void deleteSeanceclasse(Long id);
    Seanceclasse getSeanceclasseById(Long id);
    List<Seanceclasse> getAllSeanceclasses();
}
