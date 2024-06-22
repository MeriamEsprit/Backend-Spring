package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.repositories.ModuleRepository;

import java.util.List;
@Service
public class MatiereServicesImp implements IMatiereServices {

    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    public Matiere saveMatiere(Matiere matiere, Long moduleId) {
        if (moduleId != null) {
            Module module = moduleRepository.findById(moduleId).orElse(null);
            if (module == null) {
                throw new IllegalArgumentException("Module not found");
            }
            matiere.setModule(module);
        }
        return matiereRepository.save(matiere);
    }

    public Matiere updateMatiere(Long id, Matiere matiereDetails, Long moduleId) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matiere not found"));

        matiere.setNomMatiere(matiereDetails.getNomMatiere());
        matiere.setNbreHeures(matiereDetails.getNbreHeures());
        matiere.setCoefficientTP(matiereDetails.getCoefficientTP());
        matiere.setCoefficientCC(matiereDetails.getCoefficientCC());
        matiere.setCoefficientExamen(matiereDetails.getCoefficientExamen());

        if (moduleId != null) {
            Module module = moduleRepository.findById(moduleId).orElse(null);
            if (module == null) {
                throw new IllegalArgumentException("Module not found");
            }
            matiere.setModule(module);
        }
        return matiereRepository.save(matiere);
    }

    @Override
    public void deleteMatiere(Long id) {
        matiereRepository.deleteById(id);
    }

    @Override
    public Matiere getMatiereById(Long id) {
        return matiereRepository.findById(id).orElse(null);
    }

    @Override
    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }
}
