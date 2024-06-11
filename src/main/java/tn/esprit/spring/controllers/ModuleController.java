package tn.esprit.spring.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.services.ModuleServicesImpl;

import java.util.List;

@RestController
@RequestMapping("/modules")
public class ModuleController {
    private ModuleServicesImpl moduleService;

    @PostMapping
    public Module saveModule(@RequestBody Module module) {
        return moduleService.saveModule(module);
    }

    @PutMapping
    public Module updateModule(@RequestBody Module module) {
        return moduleService.updateModule(module);
    }

    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
    }

    @GetMapping("/{id}")
    public Module getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @GetMapping
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }
}
