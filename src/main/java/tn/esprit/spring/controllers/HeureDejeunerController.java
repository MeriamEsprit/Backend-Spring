package tn.esprit.spring.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.HeureDejeuner;
import tn.esprit.spring.services.HeureDejeunerServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/heuresDejeuner")
public class HeureDejeunerController {
    private final HeureDejeunerServiceImpl heureDejeunerService;

    public HeureDejeunerController(HeureDejeunerServiceImpl heureDejeunerService) {
        this.heureDejeunerService = heureDejeunerService;
    }


    @GetMapping("")
    public List<HeureDejeuner> getAllHeuresDejeuner() {
        return heureDejeunerService.getAllHeuresDejeuner();
    }

    @PostMapping("")
    public HeureDejeuner createHeureDejeuner(@RequestBody HeureDejeuner heureDejeuner) {
        return heureDejeunerService.addHeureDejeuner(heureDejeuner);
    }

    @PutMapping("/{id}")
    public HeureDejeuner updateHeureDejeuner(@PathVariable Long id, @RequestBody HeureDejeuner heureDejeuner) {
        return heureDejeunerService.updateHeureDejeuner(id, heureDejeuner);
    }
}
