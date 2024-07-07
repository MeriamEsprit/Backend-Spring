package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.DtoReclamation;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.services.EmailService;
import tn.esprit.spring.services.IReclamationServices;
import tn.esprit.spring.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/reclamations")
public class ReclamationController {

    @Autowired
    private IReclamationServices reclamationServices;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<DtoReclamation> createReclamation(@RequestBody DtoReclamation dtoReclamation) {
        Utilisateur user = userService.getInfo();
        dtoReclamation.setUtilisateurId(user.getId());
        System.out.println(dtoReclamation.getSubject());
        DtoReclamation createdReclamation = reclamationServices.createReclamation(dtoReclamation);

        return ResponseEntity.ok(createdReclamation);
    }

    @PutMapping
    public ResponseEntity<DtoReclamation> updateReclamation(@RequestBody DtoReclamation dtoReclamation) {
        DtoReclamation updatedReclamation = reclamationServices.updateReclamation(dtoReclamation);
        return ResponseEntity.ok(updatedReclamation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        reclamationServices.deleteReclamation(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoReclamation> getReclamationById(@PathVariable Long id) {
        DtoReclamation dtoReclamation = reclamationServices.getReclamationById(id);
        return ResponseEntity.ok(dtoReclamation);
    }

    @GetMapping
    public ResponseEntity<List<DtoReclamation>> getAllReclamations() {
        List<DtoReclamation> reclamations = reclamationServices.getAllReclamations();
        return ResponseEntity.ok(reclamations);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DtoReclamation>> getAllReclamationsByUser(@PathVariable Long userId) {
        List<DtoReclamation> reclamations = reclamationServices.getAllReclamationsByUser(userId);
        return ResponseEntity.ok(reclamations);
    }
    @GetMapping("/user")
    public ResponseEntity<List<DtoReclamation>> getAllReclamationsByUser() {
        Utilisateur user = userService.getInfo();
        List<DtoReclamation> reclamations = reclamationServices.getAllReclamationsByUser(user.getId());
        return ResponseEntity.ok(reclamations);
    }
}