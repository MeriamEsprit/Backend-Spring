package tn.esprit.spring.controllers;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.services.PresenceServiceImpl;
import tn.esprit.spring.services.UserService;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    private PresenceServiceImpl presenceService;

    @Autowired
    public PresenceController(PresenceServiceImpl presenceService) {
        this.presenceService = presenceService;
    }


    @PostMapping("/addPresence")
    public Presence addPresence (@RequestBody Presence presence){
        return presenceService.addPresence(presence);
    }

    @GetMapping("/findUsersByRoleAndClasse")
    public List<Utilisateur> findUsersByRoleAndClasse(@RequestParam String role, @RequestParam Long id) {
        return presenceService.getUsersByRoleAndClassId(role, id);
    }

    @GetMapping("/findUserByRoleAndIdAndClasse")
    public Utilisateur findUserByRoleAndIdAndClasseId(@RequestParam String role,@RequestParam Long id_Utilisateur, @RequestParam Long id) {
        return presenceService.getUserByRoleAndIdAndClasseId(role,id_Utilisateur,id);
    }
    @GetMapping("/findAllPresences")
    public List<Presence> findAllPresences() {
        return presenceService.getAllPresences();
    }
    @PostMapping("/addPresenceForEtudiant")
    public Presence addPresenceForEtudiant(@RequestParam Long studentId, @RequestBody Presence presenceDetails) {
        return presenceService.addPresenceForEtudiant(studentId, presenceDetails);
    }


}
