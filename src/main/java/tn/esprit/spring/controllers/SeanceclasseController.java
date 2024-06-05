package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Seanceclasse;
import tn.esprit.spring.services.SeanceclasseServicesImpl;

import java.util.List;

@RestController
@RequestMapping("/seanceclasses")
public class SeanceclasseController {

    @Autowired
    private SeanceclasseServicesImpl seanceclasseService;

    @PostMapping
    public Seanceclasse saveSeanceclasse(@RequestBody Seanceclasse seanceclasse) {
        return seanceclasseService.saveSeanceclasse(seanceclasse);
    }

    @PutMapping
    public Seanceclasse updateSeanceclasse(@RequestBody Seanceclasse seanceclasse) {
        return seanceclasseService.updateSeanceclasse(seanceclasse);
    }

    @DeleteMapping("/{id}")
    public void deleteSeanceclasse(@PathVariable Long id) {
        seanceclasseService.deleteSeanceclasse(id);
    }

    @GetMapping("/{id}")
    public Seanceclasse getSeanceclasseById(@PathVariable Long id) {
        return seanceclasseService.getSeanceclasseById(id);
    }

    @GetMapping
    public List<Seanceclasse> getAllSeanceclasses() {
        return seanceclasseService.getAllSeanceclasses();
    }
}
