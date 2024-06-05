package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Emploidutemp;
import tn.esprit.spring.services.EmploiServicesImpl;

import java.util.List;

@RestController
@RequestMapping("/emplois")
public class EmploiController {

    @Autowired
    private EmploiServicesImpl emploiService;

    @PostMapping
    public Emploidutemp saveEmploi(@RequestBody Emploidutemp Emploidutemp) {
        return emploiService.saveEmploi(Emploidutemp);
    }

    @PutMapping
    public Emploidutemp updateEmploi(@RequestBody Emploidutemp Emploidutemp) {
        return emploiService.updateEmploi(Emploidutemp);
    }

    @DeleteMapping("/{id}")
    public void deleteEmploidutemp(@PathVariable Long id) {
        emploiService.deleteEmploidutemp(id);
    }

    @GetMapping("/{id}")
    public Emploidutemp getEmploiById(@PathVariable Long id) {
        return emploiService.getEmploiById(id);
    }

    @GetMapping
    public List<Emploidutemp> getAllEmplois() {
        return emploiService.getAllEmplois();
    }
}
