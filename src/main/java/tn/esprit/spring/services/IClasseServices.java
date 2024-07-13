package tn.esprit.spring.services;

import tn.esprit.spring.Dto.emploiDuTemps.ClasseDTO;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Utilisateur;

import java.util.List;

public interface IClasseServices {
    //Classe saveClasse(Classe classe);

    Classe saveClasse(ClasseDTO classeDTO);

    Classe updateClasse(Classe classe);
    void deleteClasse(Long id);
    Classe getClasseById(Long id);
    List<Classe> getAllClasses();

    List<Utilisateur> getUsersByClass(Long classeId);

    Classe assignerMatieresAClasse(Long idClasse, List<Long> idMatieres);

    List<Matiere> getMatieresByClasse(Long classeId);
}
