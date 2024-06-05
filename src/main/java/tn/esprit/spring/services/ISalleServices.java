package tn.esprit.spring.services;

import tn.esprit.spring.entities.Salle;

import java.util.List;

public interface ISalleServices {
    Salle saveSalle(Salle salle);
    Salle updateSalle(Salle salle);
    void deleteSalle(Long id);
    Salle getSalleById(Long id);
    List<Salle> getAllSalles();
}

