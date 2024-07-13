package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.emploiDuTemps.ClasseDTO;
import tn.esprit.spring.Dto.emploiDuTemps.ClasseMatiereDTO;
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
    public Classe saveClasse(@RequestBody ClasseDTO classeDTO) {
        return classeService.saveClasse(classeDTO);
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

    @GetMapping("/{id}/users")
    public ResponseEntity<List<Utilisateur>> getUsersByClass(@PathVariable Long id) {
        List<Utilisateur> users = classeService.getUsersByClass(id);
        return ResponseEntity.ok(users);
    }
/*
    @GetMapping("/{classeId}/matieres")
    public ResponseEntity<List<Matiere>> getMatieresByClass(@PathVariable Long classeId) {
        List<Matiere> matieres = classeService.getMatieresByClass(classeId);
        return ResponseEntity.ok(matieres);
    }*/

    @PostMapping("/matieres")
    public Classe assignerMatieresAClasse(@RequestBody ClasseMatiereDTO classeMatiereDTO) {
        return classeService.assignerMatieresAClasse(classeMatiereDTO.getIdClasse(), classeMatiereDTO.getIdMatieres());
    }

    @GetMapping("/{classeId}/matieres")
    public List<Matiere> getMatieresByClasse(@PathVariable Long classeId) {
        return classeService.getMatieresByClasse(classeId);
    }



}
