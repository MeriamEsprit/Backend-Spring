package tn.esprit.spring.services;

import org.apache.poi.sl.usermodel.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Dto.ConversionUtil;
import tn.esprit.spring.Dto.MatiereDTO;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.repositories.ModuleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatiereServicesImp implements IMatiereServices {

    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Override
    public MatiereDTO saveMatiere(MatiereDTO matiereDTO) {
        Module module = moduleRepository.findById(matiereDTO.getModuleId())
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));
        Matiere matiere = ConversionUtil.convertToMatiereEntity(matiereDTO, module);
        Matiere savedMatiere = matiereRepository.save(matiere);
        return ConversionUtil.convertToMatiereDTO(savedMatiere);
    }

    @Override
    public MatiereDTO updateMatiere(Long id, MatiereDTO matiereDTO) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matiere not found")); // Ensure matiere is found

        Module module = moduleRepository.findById(matiereDTO.getModuleId())
                .orElseThrow(() -> new IllegalArgumentException("Module not found")); // Ensure module is found

        matiere.setNomMatiere(matiereDTO.getNomMatiere());
        matiere.setNbreHeures(matiereDTO.getNbreHeures());
        matiere.setCoefficientTP(matiereDTO.getCoefficientTP());
        matiere.setCoefficientCC(matiereDTO.getCoefficientCC());
        matiere.setCoefficientExamen(matiereDTO.getCoefficientExamen());
        matiere.setModule(module); // Set the module

        Matiere updatedMatiere = matiereRepository.save(matiere);
        return ConversionUtil.convertToMatiereDTO(updatedMatiere);
    }

    @Override
    public void deleteMatiere(Long id) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matiere not found"));

        // Disassociate matiere from classes
        for (Classe classe : matiere.getClasses()) {
            classe.getMatieres().remove(matiere);
        }
        for (Note note : matiere.getNotes()) {
            note.setMatiere(null);
        }

        matiereRepository.delete(matiere);    }

    @Override
    public MatiereDTO getMatiereById(Long id) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matiere not found"));
        return ConversionUtil.convertToMatiereDTO(matiere);
    }

    @Override
    public List<MatiereDTO> getAllMatieres() {
        List<Matiere> matieres = matiereRepository.findAll();
        return matieres.stream()
                .map(ConversionUtil::convertToMatiereDTO)
                .collect(Collectors.toList());
    }
    public boolean isNomMatiereUnique(String nomMatiere) {
        return !matiereRepository.existsByNomMatiere(nomMatiere);
    }
    public boolean isNomMatiereUniqueExceptCurrent(String nomMatiere, Long id) {
        Matiere existingMatiere = matiereRepository.findById(id).orElse(null);
        if (existingMatiere != null && existingMatiere.getNomMatiere().equals(nomMatiere)) {
            return true;
        }
        return !matiereRepository.existsByNomMatiere(nomMatiere);
    }
}
