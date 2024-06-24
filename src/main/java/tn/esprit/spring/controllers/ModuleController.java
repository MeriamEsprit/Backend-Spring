package tn.esprit.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.ModuleDTO;
import tn.esprit.spring.services.ModuleServicesImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/modules")
public class ModuleController {

    private ModuleServicesImpl moduleService;

    @PostMapping
    public ResponseEntity<ModuleDTO> saveModule(@RequestBody ModuleDTO moduleDTO) {
        try {
            ModuleDTO createdModule = moduleService.saveModule(moduleDTO);
            return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{moduleId}")
    public ResponseEntity<ModuleDTO> updateModule(@PathVariable("moduleId") Long moduleId, @RequestBody ModuleDTO moduleDTO) {
        try {
            ModuleDTO updatedModule = moduleService.updateModule(moduleId, moduleDTO);
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
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable Long id) {
        ModuleDTO moduleDTO = moduleService.getModuleById(id);
        if (moduleDTO != null) {
            return new ResponseEntity<>(moduleDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ModuleDTO>> getAllModules() {
        List<ModuleDTO> modules = moduleService.getAllModules();
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }
}
