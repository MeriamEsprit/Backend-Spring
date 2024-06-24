package tn.esprit.spring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.repositories.ModuleRepository;


import java.util.ArrayList;
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
    public Module updateModule(Long moduleId, Module module) {
        Module existingModule = getModuleById(moduleId);
        if (existingModule != null) {
            existingModule.setNom(module.getNom());
            existingModule.setDescription(module.getDescription());

            // Update the list of matieres
            List<Matiere> updatedMatieres = new ArrayList<>(existingModule.getMatieres());

            // Add new matieres to the list
            for (Matiere matiere : module.getMatieres()) {
                if (!updatedMatieres.contains(matiere)) {
                    updatedMatieres.add(matiere);
                }
            }
            // Remove matieres from the list
            updatedMatieres.removeIf(matiere -> !module.getMatieres().contains(matiere));

            existingModule.setMatieres(updatedMatieres);

            return moduleRepository.save(existingModule);
        } else {
            throw new IllegalArgumentException("Module with ID " + moduleId + " not found");
        }
    }

    public Module updateModuleWithMatieres(Long moduleId, Module module) {
        Module existingModule = getModuleById(moduleId);

        existingModule.setNom(module.getNom());
        existingModule.setDescription(module.getDescription());

        List<Matiere> updatedMatieres = new ArrayList<>(existingModule.getMatieres());

        // Check if module.getMatieres() is not null before adding elements
        if (module.getMatieres() != null) {
            updatedMatieres.addAll(module.getMatieres());
        }

        existingModule.setMatieres(updatedMatieres);

        return moduleRepository.save(existingModule);
    }



    public Module addMatiereToModule(Long moduleId, Long matiereId) {
        Module module = getModuleById(moduleId);
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new IllegalArgumentException("Matiere not found with id " + matiereId));
        module.getMatieres().add(matiere);
        return moduleRepository.save(module);
    }

    public Module removeMatiereFromModule(Long moduleId, Long matiereId) {
        Module module = getModuleById(moduleId);
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new IllegalArgumentException("Matiere not found with id " + matiereId));
        module.getMatieres().remove(matiere);
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
