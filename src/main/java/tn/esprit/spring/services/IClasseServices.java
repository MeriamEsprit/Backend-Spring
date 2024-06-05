package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;

import java.util.List;

public interface IClasseServices {
    Classe saveClasse(Classe classe);
    Classe updateClasse(Classe classe);
    void deleteClasse(Long id);
    Classe getClasseById(Long id);
    List<Classe> getAllClasses();
}
