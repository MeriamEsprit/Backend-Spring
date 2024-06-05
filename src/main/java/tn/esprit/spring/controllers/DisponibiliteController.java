package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Disponibilite;
import tn.esprit.spring.services.DisponibiliteServicesImpl;

import java.util.List;

@RestController
@RequestMapping("/disponibilites")
public class DisponibiliteController {

    @Autowired
    private DisponibiliteServicesImpl disponibiliteService;

    @PostMapping
    public Disponibilite saveDisponibilite(@RequestBody Disponibilite disponibilite) {
        return disponibiliteService.saveDisponibilite(disponibilite);
    }

    @PutMapping
    public Disponibilite updateDisponibilite(@RequestBody Disponibilite disponibilite) {
        return disponibiliteService.updateDisponibilite(disponibilite);
    }

    @DeleteMapping("/{id}")
    public void deleteDisponibilite(@PathVariable Long id) {
        disponibiliteService.deleteDisponibilite(id);
    }

    @GetMapping("/{id}")
    public Disponibilite getDisponibiliteById(@PathVariable Long id) {
        return disponibiliteService.getDisponibiliteById(id);
    }

    @GetMapping
    public List<Disponibilite> getAllDisponibilites() {
        return disponibiliteService.getAllDisponibilites();
    }
}
