package tn.esprit.spring.utils;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.repositories.*;

import java.util.*;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Transactional
    public void initializeModulesAndMatieres() {
        List<String> moduleNames = Arrays.asList("Module1", "Module2", "Module3", "Module4", "Module5");
        List<String> matiereNames = Arrays.asList(
                "Matiere1", "Matiere2", "Matiere3", "Matiere4", "Matiere5",
                "Matiere6", "Matiere7", "Matiere8", "Matiere9", "Matiere10",
                "Matiere11", "Matiere12", "Matiere13", "Matiere14", "Matiere15",
                "Matiere16", "Matiere17", "Matiere18", "Matiere19", "Matiere20",
                "Matiere21", "Matiere22", "Matiere23", "Matiere24", "Matiere25",
                "Matiere26", "Matiere27", "Matiere28", "Matiere29", "Matiere30"
        );

        Set<String> assignedMatieres = new HashSet<>();
        Random random = new Random();

        for (String moduleName : moduleNames) {
            Optional<Module> existingModule = moduleRepository.findByNom(moduleName);
            Module module;
            if (existingModule.isPresent()) {
                module = existingModule.get();
                // Initialize the lazy-loaded collection
                module.getMatieres().size();
            } else {
                module = new Module();
                module.setNom(moduleName);
                module.setDescription("Description for " + moduleName);
                moduleRepository.save(module);
                System.out.println("Saved module: " + moduleName);
            }

            int matiereCount = random.nextInt(3) + 3; // Random number between 3 and 5
            int projectCount = 0;

            for (String matiereName : matiereNames) {
                if (assignedMatieres.contains(matiereName)) continue;
                if (module.getMatieres().size() >= matiereCount) break;

                if (matiereRepository.findByNomMatiere(matiereName).isPresent()) {
                    assignedMatieres.add(matiereName);
                    continue;
                }

                Matiere matiere = new Matiere();
                matiere.setNomMatiere(matiereName);
                matiere.setCoefficientTP(0.4);
                matiere.setCoefficientCC(0.0);
                matiere.setCoefficientExamen(0.6);
                matiere.setNbreHeures(50);
                matiere.setCoefficient(random.nextInt(4) + 1); // Random coefficient between 1 and 4

                if (projectCount < 3) {
                    matiere.setType(TypeMatiere.PROJET);
                    matiere.setModule(null);
                    projectCount++;
                } else {
                    matiere.setType(TypeMatiere.STANDARD);
                    matiere.setModule(module);
                }

                matiereRepository.save(matiere);
                if (matiere.getType() == TypeMatiere.STANDARD) {
                    module.getMatieres().add(matiere); // Ensure matiere is added to module's list
                }
                assignedMatieres.add(matiere.getNomMatiere());
                System.out.println("Saved matiere: " + matiere.getNomMatiere());
            }
        }

        List<Matiere> allMatieres = matiereRepository.findAll();

        for (Classe classe : classeRepository.findAll()) {
            Set<Module> assignedModules = new HashSet<>();
            Set<Matiere> assignedMatieresToClass = new HashSet<>();
            int projectCount = 0;

            Collections.shuffle(allMatieres); // Randomize the order of Matieres

            for (Matiere matiere : allMatieres) {
                if (assignedMatieresToClass.size() >= 14) break;
                if (assignedMatieresToClass.contains(matiere)) continue;
                if (matiere.getType() == TypeMatiere.PROJET && projectCount >= 3) continue;

                assignedMatieresToClass.add(matiere);
                if (matiere.getType() == TypeMatiere.PROJET) projectCount++;

                // Assign all matieres from the same module
                Module module = matiere.getModule();
                if (module != null && !assignedModules.contains(module)) {
                    assignedModules.add(module);
                    assignedMatieresToClass.addAll(module.getMatieres());
                }
            }

            classe.setMatieres(new ArrayList<>(assignedMatieresToClass));
            classeRepository.save(classe);
            System.out.println("Assigned matieres to classe " + classe.getNomClasse());
        }
    }
}
