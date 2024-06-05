package tn.esprit.spring.services;

import tn.esprit.spring.entities.Emploidutemp;

import java.util.List;

public interface IEmploiServices {
    Emploidutemp saveEmploi(Emploidutemp Emploidutemp);
    Emploidutemp updateEmploi(Emploidutemp Emploidutemp);
    void deleteEmploidutemp(Long id);
    Emploidutemp getEmploiById(Long id);
    List<Emploidutemp> getAllEmplois();
}


