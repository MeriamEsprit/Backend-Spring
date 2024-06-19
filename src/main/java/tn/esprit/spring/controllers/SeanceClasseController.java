package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.emploiDuTemps.SeanceClasseDTO;
import tn.esprit.spring.entities.SeanceClasse;
import tn.esprit.spring.services.SeanceclasseServicesImpl;


import java.util.List;

@RestController
@RequestMapping("/api/seanceClasses")
public class SeanceClasseController {

    @Autowired
    private SeanceclasseServicesImpl seanceClasseService;

    @PostMapping
    /*public SeanceClasse saveSeanceClasse(@RequestBody SeanceClasse seanceclasse) {

        return seanceClasseService.saveSeanceclasse(seanceclasse);
    }*/
    public ResponseEntity<SeanceClasse> addSeanceClasse(@RequestBody SeanceClasseDTO seanceClasseDTO) {
        SeanceClasse addedSeance = seanceClasseService.addSeanceClasse(seanceClasseDTO);
        return ResponseEntity.ok(addedSeance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeanceClasse> updateSeanceClasse(@PathVariable Long id, @RequestBody SeanceClasseDTO seanceClasseDTO) {
        SeanceClasse updatedSeance = seanceClasseService.updateSeanceClasse(id, seanceClasseDTO);
        return ResponseEntity.ok(updatedSeance);
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


}
