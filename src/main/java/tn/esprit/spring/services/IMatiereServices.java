package tn.esprit.spring.services;

import tn.esprit.spring.Dto.MatiereDTO;
import tn.esprit.spring.entities.Matiere;

import java.util.List;

public interface IMatiereServices {
    MatiereDTO saveMatiere(MatiereDTO matiereDTO);
    MatiereDTO updateMatiere(Long id, MatiereDTO matiereDTO);
    void deleteMatiere(Long id);
    MatiereDTO getMatiereById(Long id);
    List<MatiereDTO> getAllMatieres();
}
