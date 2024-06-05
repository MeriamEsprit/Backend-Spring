package tn.esprit.spring.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.services.MatiereServicesImp;

import java.util.List;

@RestController
@RequestMapping("/matieres")
public class MatiereController {
    private MatiereServicesImp matiereService;
    @PostMapping
    public Matiere saveMatiere(@RequestBody Matiere matiere) {
        return matiereService.saveMatiere(matiere);
    }

    @PutMapping
    public Matiere updateMatiere(@RequestBody Matiere matiere) {
        return matiereService.updateMatiere(matiere);
    }

    @DeleteMapping("/{id}")
    public void deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
    }

    @GetMapping("/{id}")
    public Matiere getMatiereById(@PathVariable Long id) {
        return matiereService.getMatiereById(id);
    }

    @GetMapping
    public List<Matiere> getAllMatieres() {
        return matiereService.getAllMatieres();
    }
}
