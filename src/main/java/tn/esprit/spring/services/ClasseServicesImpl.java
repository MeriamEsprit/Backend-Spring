package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;

import java.util.List;

@Service
public class ClasseServicesImpl implements IClasseServices {

    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Classe saveClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    public Classe updateClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }

    @Override
    public Classe getClasseById(Long id) {
        return classeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    public List<Utilisateur> getUsersByClass(Long classeId) {
        return utilisateurRepository.findByClasseId(classeId);
    }
    public List<Matiere> getMatieresByClass(Long classeId) {
        Classe classe = classeRepository.findById(classeId).orElseThrow(() -> new RuntimeException("Classe not found"));
        return classe.getMatieres();
    }
    
}
