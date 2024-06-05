package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Competence;
import tn.esprit.spring.services.CompetenceServicesImpl;

import java.util.List;

@RestController
@RequestMapping("/competences")
public class CompetenceController {

    @Autowired
    private CompetenceServicesImpl competenceService;

    @PostMapping
    public Competence saveCompetence(@RequestBody Competence competence) {
        return competenceService.saveCompetence(competence);
    }

    @PutMapping
    public Competence updateCompetence(@RequestBody Competence competence) {
        return competenceService.updateCompetence(competence);
    }

    @DeleteMapping("/{id}")
    public void deleteCompetence(@PathVariable Long id) {
        competenceService.deleteCompetence(id);
    }

    @GetMapping("/{id}")
    public Competence getCompetenceById(@PathVariable Long id) {
        return competenceService.getCompetenceById(id);
    }

    @GetMapping
    public List<Competence> getAllCompetences() {
        return competenceService.getAllCompetences();
    }
}
