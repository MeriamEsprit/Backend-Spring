package tn.esprit.spring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.repositories.ModuleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleServicesImpl implements IModuleServices{

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Override
    public Module saveModule(Module module) {
        checkUniqueModuleName(module.getNom());
        if (module.getMatieres() != null) {
            checkMatieresExist(module.getMatieres());
        }
        return moduleRepository.save(module);
    }

    @Override
    public Module updateModule(Module module) {
        checkUniqueModuleName(module.getNom());
        if (module.getMatieres() != null) {
            checkMatieresExist(module.getMatieres());
        }
        return moduleRepository.save(module);
    }

    @Override
    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }

    @Override
    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    private void checkMatieresExist(List<Matiere> matieres) {
        for (Matiere matiere : matieres) {
            Optional<Matiere> existingMatiere = matiereRepository.findById(matiere.getId());
            if (existingMatiere.isEmpty()) {
                throw new IllegalArgumentException("Matiere with ID " + matiere.getId() + " does not exist");
            }
        }
    }

    private void checkUniqueModuleName(String nom) {
        Optional<Module> existingModule = moduleRepository.findByNom(nom);
        if (existingModule.isPresent()) {
            throw new IllegalArgumentException("Module with name " + nom + " already exists");
        }
    }

}
