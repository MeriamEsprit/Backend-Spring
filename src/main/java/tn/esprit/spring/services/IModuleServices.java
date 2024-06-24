package tn.esprit.spring.services;

import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;

import java.util.List;

public interface IModuleServices {
//   Module saveModule(Module module);
    Module updateModule(Long moduleId,Module module);
    void deleteModule(Long id);
    Module getModuleById(Long id);
    List<Module> getAllModules();

}
