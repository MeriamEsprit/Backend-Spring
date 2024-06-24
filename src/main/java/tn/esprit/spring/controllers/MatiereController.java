package tn.esprit.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.MatiereDTO;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.services.MatiereServicesImp;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/matieres")
public class MatiereController {
    private MatiereServicesImp matiereService;
    @PostMapping
    public ResponseEntity<MatiereDTO> createMatiere(@RequestBody MatiereDTO matiereDTO) {
        try {
            MatiereDTO savedMatiere = matiereService.saveMatiere(matiereDTO);
            return new ResponseEntity<>(savedMatiere, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatiereDTO> updateMatiere(@PathVariable("id") Long id, @RequestBody MatiereDTO matiereDetails) {
        try {
            MatiereDTO updatedMatiere = matiereService.updateMatiere(id, matiereDetails);
            return new ResponseEntity<>(updatedMatiere, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
    }

    @GetMapping("/{id}")
    public MatiereDTO getMatiereById(@PathVariable Long id) {
        return matiereService.getMatiereById(id);
    }

    @GetMapping
    public List<MatiereDTO> getAllMatieres() {
        return matiereService.getAllMatieres();
    }
}
