package tn.esprit.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.services.ModuleServicesImpl;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/modules")
public class ModuleController {

    private ModuleServicesImpl moduleService;

    @PostMapping
    public ResponseEntity<Module> saveModule(@RequestBody Module module) {
        try {
            Module createdModule = moduleService.saveModule(module);
            return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Module> updateModule(@RequestBody Module module) {
        try {
            Module updatedModule = moduleService.updateModule(module);
            return new ResponseEntity<>(updatedModule, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable Long id) {
        Module module = moduleService.getModuleById(id);
        if (module != null) {
            return new ResponseEntity<>(module, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Module>> getAllModules() {
        List<Module> modules = moduleService.getAllModules();
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }
}
