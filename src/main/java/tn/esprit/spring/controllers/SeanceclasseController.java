package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.SeanceClasse;
import tn.esprit.spring.services.SeanceclasseServicesImpl;

import java.util.List;

@RestController
@RequestMapping("/api/seanceclasses")
public class SeanceclasseController {

    @Autowired
    private SeanceclasseServicesImpl seanceclasseService;

    @PostMapping
    public SeanceClasse saveSeanceclasse(@RequestBody SeanceClasse seanceclasse) {
        return seanceclasseService.saveSeanceclasse(seanceclasse);
    }

    @PutMapping
    public SeanceClasse updateSeanceclasse(@RequestBody SeanceClasse seanceclasse) {
        return seanceclasseService.updateSeanceclasse(seanceclasse);
    }

    @DeleteMapping("/{id}")
    public void deleteSeanceclasse(@PathVariable Long id) {
        seanceclasseService.deleteSeanceclasse(id);
    }

    @GetMapping("/{id}")
    public SeanceClasse getSeanceclasseById(@PathVariable Long id) {
        return seanceclasseService.getSeanceclasseById(id);
    }

    @GetMapping
    public List<SeanceClasse> getAllSeanceclasses() {
        return seanceclasseService.getAllSeanceclasses();
    }
}
