package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.services.ClasseServicesImpl;

import java.util.List;

@RestController
@RequestMapping("/api/classe")

public class ClasseController {

    @Autowired
    private ClasseServicesImpl classeService;

    @PostMapping
    public Classe saveClasse(@RequestBody Classe classe) {
        return classeService.saveClasse(classe);
    }

    @PutMapping
    public Classe updateClasse(@RequestBody Classe classe) {
        return classeService.updateClasse(classe);
    }

    @DeleteMapping("/{id}")
    public void deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
    }

    @GetMapping("/{id}")
    public Classe getClasseById(@PathVariable Long id) {
        return classeService.getClasseById(id);
    }

    @GetMapping
    public List<Classe> getAllClasses() {
        return classeService.getAllClasses();
    }

    @GetMapping("/{classeId}/users")
    public ResponseEntity<List<Utilisateur>> getUsersByClass(@PathVariable Long classeId) {
        List<Utilisateur> users = classeService.getUsersByClass(classeId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{classeId}/matieres")
    public ResponseEntity<List<Matiere>> getMatieresByClass(@PathVariable Long classeId) {
        List<Matiere> matieres = classeService.getMatieresByClass(classeId);
        return ResponseEntity.ok(matieres);
    }



}
