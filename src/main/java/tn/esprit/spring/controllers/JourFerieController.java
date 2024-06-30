package tn.esprit.spring.controllers;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.JourFerie;
import tn.esprit.spring.services.JourFerieServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/jourFerie")
public class JourFerieController {
    private final JourFerieServiceImpl jourFerieService;

    public JourFerieController(JourFerieServiceImpl jourFerieService) {
        this.jourFerieService = jourFerieService;
    }

    @GetMapping("")
    public List<JourFerie> getAllJoursFeries() {
        return jourFerieService.getAllJourFeries();
    }

    @PostMapping("")
    public JourFerie createJourFerie(@RequestBody JourFerie jourFerie) {
        return jourFerieService.addJourFerie(jourFerie);
    }

    @PutMapping("/{id}")
    public JourFerie updateJourFerie(@PathVariable Long id, @RequestBody JourFerie jourFerie) {
        return jourFerieService.updateJourFerie(id, jourFerie);
    }
}
