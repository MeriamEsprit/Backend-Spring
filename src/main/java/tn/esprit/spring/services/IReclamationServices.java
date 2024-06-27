package tn.esprit.spring.services;

import tn.esprit.spring.Dto.DtoReclamation;

import java.util.List;

public interface IReclamationServices {
    DtoReclamation createReclamation(DtoReclamation dtoReclamation);
    DtoReclamation updateReclamation(DtoReclamation dtoReclamation);
    void deleteReclamation(Long id);
    DtoReclamation getReclamationById(Long id);
    List<DtoReclamation> getAllReclamations();
}
