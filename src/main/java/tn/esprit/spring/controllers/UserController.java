package tn.esprit.spring.controllers;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.services.ClasseServicesImpl;
import tn.esprit.spring.services.PresenceServiceImpl;
import tn.esprit.spring.services.UserService;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private PresenceServiceImpl presenceService;

    @Autowired
    private ClasseServicesImpl classeService;
    @GetMapping("/all-enseignant")
    // /user/all-enseignant
    public ResponseEntity<?> getAllEnseignant() {
        try {
            List<?> enseignants = userService.getAllUserByRole(ERole.ROLE_TEACHER);
            return ResponseEntity.ok(enseignants);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-etudiant")
    // /user/all-etudiant
    public ResponseEntity<?> getAllEtudiant() {
        try {
            List<?> enseignants = userService.getAllUserByRole(ERole.ROLE_STUDENT);
            return ResponseEntity.ok(enseignants);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByRoleAndClass")
    public List<Utilisateur> findByRoleAndClass (@RequestParam ERole role, @RequestParam Long classId){
        Classe classe = classeService.getClasseById(classId);
        return userService.getUtilisateursByRoleAndClasse(role,classe);
    }

    //Start Modification Nassreddine
//    @GetMapping("/findAllByClasse")
//    public List<Utilisateur> findUsersByClasseId(@RequestParam Long id) {
//        return userService.findByClasseId(id);
//    }
//
//    @GetMapping("/findUsersByRoleAndClasse")
//    public List<Utilisateur>findUsersByRoleAndClasseId(@RequestParam ERole role, @RequestParam Long id){
//        return userService.findUsersByRoleAndClasseId(role,id);
    }
    //End Modification Nassreddine

