package tn.esprit.spring.services;

import tn.esprit.spring.entities.HeureDejeuner;

import java.util.List;

public interface IHeureDejeunerService {
    List<HeureDejeuner> getAllHeuresDejeuner();

    HeureDejeuner addHeureDejeuner(HeureDejeuner heureDejeuner);

    HeureDejeuner updateHeureDejeuner(Long id, HeureDejeuner heureDejeuner);
}
