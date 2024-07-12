package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Dto.ConversionUtil;
import tn.esprit.spring.Dto.ModuleDTO;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.repositories.ModuleRepository;
import tn.esprit.spring.repositories.NoteRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModuleServicesImpl implements IModuleServices {

    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public ModuleDTO saveModule(ModuleDTO moduleDTO) {
        checkUniqueModuleName(moduleDTO.getNom());
        Module module = ConversionUtil.convertToModuleEntity(moduleDTO);
        Module savedModule = moduleRepository.save(module);
        return ConversionUtil.convertToModuleDTO(savedModule);
    }

    @Override
    public ModuleDTO updateModule(Long moduleId, ModuleDTO moduleDTO) {
        Module existingModule = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module with ID " + moduleId + " not found"));

        existingModule.setNom(moduleDTO.getNom());
        existingModule.setDescription(moduleDTO.getDescription());

        Module updatedModule = moduleRepository.save(existingModule);
        return ConversionUtil.convertToModuleDTO(updatedModule);
    }

    @Override
    public void deleteModule(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Module not found with id " + id));
        moduleRepository.delete(module);
    }

    @Override
    public ModuleDTO getModuleById(Long id) {
        Module module = moduleRepository.findById(id).orElse(null);
        if (module != null) {
            return ConversionUtil.convertToModuleDTO(module);
        }
        return null;
    }

    @Override
    public List<ModuleDTO> getAllModules() {
        List<Module> modules = moduleRepository.findAll();
        return modules.stream()
                .map(ConversionUtil::convertToModuleDTO)
                .collect(Collectors.toList());
    }

    private void checkUniqueModuleName(String nom) {
        Optional<Module> existingModule = moduleRepository.findByNom(nom);
        if (existingModule.isPresent()) {
            throw new IllegalArgumentException("Module with name " + nom + " already exists");
        }
    }
    public long count() {
        return moduleRepository.count();
    }

    public boolean isModuleNameUnique(String nom) {
        return !moduleRepository.existsByNom(nom);
    }

    public boolean isModuleNameUniqueExceptCurrent(String nom, Long id) {
        Optional<Module> existingModule = moduleRepository.findById(id);
        if (existingModule.isPresent() && existingModule.get().getNom().equals(nom)) {
            return true;
        }
        return !moduleRepository.existsByNom(nom);
    }



}
