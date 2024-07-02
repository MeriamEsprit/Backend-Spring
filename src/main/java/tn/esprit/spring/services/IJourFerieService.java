package tn.esprit.spring.services;

import tn.esprit.spring.entities.JourFerie;

import java.util.List;

public interface IJourFerieService {
    List<JourFerie> getAllJourFeries();

    JourFerie addJourFerie(JourFerie jourFerie);

    JourFerie updateJourFerie(Long id, JourFerie jourFerie);
}
