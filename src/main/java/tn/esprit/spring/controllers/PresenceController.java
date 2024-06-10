package tn.esprit.spring.controllers;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.services.ClasseServicesImpl;
import tn.esprit.spring.services.PresenceServiceImpl;
import tn.esprit.spring.services.UserService;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/presences")
public class PresenceController {

    private PresenceServiceImpl presenceService;
    @Autowired
    private ClasseServicesImpl classeService;

    @Autowired
    public PresenceController(PresenceServiceImpl presenceService) {
        this.presenceService = presenceService;
    }


    @PostMapping("/addPresence")
    public Presence addPresence (@RequestBody Presence presence){
        return presenceService.addPresence(presence);
    }

    @GetMapping("/getAllPresence")
    public List<Presence> findAllPresences() {
        return presenceService.getAllPresences();
    }

    @PostMapping("/addPresenceForEtudiant")
    public Presence addPresenceForEtudiant(@RequestParam Long studentId, @RequestBody Presence presenceDetails) {
        return presenceService.addPresenceForEtudiant(studentId,presenceDetails);
    }

    @GetMapping("/listutilisateursbyclasse/{idClass}")
    public List<Utilisateur>findStudentByClasse(@PathVariable Long classId)
    {
        return presenceService.findStudentByClasse(classId);
    }




}
