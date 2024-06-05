package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Salle;
import tn.esprit.spring.services.SalleServicesImpl;

import java.util.List;

@RestController
@RequestMapping("/salles")
public class SalleController {

    @Autowired
    private SalleServicesImpl salleService;

    @PostMapping
    public Salle saveSalle(@RequestBody Salle salle) {
        return salleService.saveSalle(salle);
    }

    @PutMapping
    public Salle updateSalle(@RequestBody Salle salle) {
        return salleService.updateSalle(salle);
    }

    @DeleteMapping("/{id}")
    public void deleteSalle(@PathVariable Long id) {
        salleService.deleteSalle(id);
    }

    @GetMapping("/{id}")
    public Salle getSalleById(@PathVariable Long id) {
        return salleService.getSalleById(id);
    }

    @GetMapping
    public List<Salle> getAllSalles() {
        return salleService.getAllSalles();
    }
}
