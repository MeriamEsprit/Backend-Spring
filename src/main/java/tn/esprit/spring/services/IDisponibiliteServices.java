package tn.esprit.spring.services;

import tn.esprit.spring.entities.Disponibilite;

import java.util.List;

public interface IDisponibiliteServices {
    Disponibilite saveDisponibilite(Disponibilite disponibilite);
    Disponibilite updateDisponibilite(Disponibilite disponibilite);
    void deleteDisponibilite(Long id);
    Disponibilite getDisponibiliteById(Long id);
    List<Disponibilite> getAllDisponibilites();
}
