package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.emploiDuTemps.SeanceClasseSummaryDto;
import tn.esprit.spring.entities.SeanceClasse;
import tn.esprit.spring.services.SeanceclasseServicesImpl;

import java.util.List;

@RestController
@RequestMapping("/api/seanceClasses")
public class SeanceClasseController {

    @Autowired
    private SeanceclasseServicesImpl seanceClasseService;

    @PostMapping
    public SeanceClasse saveSeanceClasse(@RequestBody SeanceClasse seanceclasse) {

        return seanceClasseService.saveSeanceclasse(seanceclasse);
    }

    @PutMapping
    public SeanceClasse updateSeanceClasse(@RequestBody SeanceClasse seanceclasse) {
        return seanceClasseService.updateSeanceclasse(seanceclasse);
    }

    @DeleteMapping("/{id}")
    public void deleteSeanceClasse(@PathVariable Long id) {
        seanceClasseService.deleteSeanceclasse(id);
    }

    @GetMapping("/{id}")
    public SeanceClasse getSeanceClasseById(@PathVariable Long id) {
        return seanceClasseService.getSeanceclasseById(id);
    }

    @GetMapping
    public List<SeanceClasse> getAllSeanceClasses() {
        return seanceClasseService.getAllSeanceclasses();
    }

    @GetMapping("/summaries")
    public List<SeanceClasseSummaryDto> getAllSeanceClasseSummaries() {
        return seanceClasseService.getAllSeanceClasseSummaries();
    }
}
