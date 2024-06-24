package tn.esprit.spring.services;

import tn.esprit.spring.Dto.ModuleDTO;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;

import java.util.List;

public interface IModuleServices {
    ModuleDTO saveModule(ModuleDTO module);
    ModuleDTO updateModule(Long moduleId,ModuleDTO module);
    void deleteModule(Long id);
    ModuleDTO getModuleById(Long id);
    List<ModuleDTO> getAllModules();

}
