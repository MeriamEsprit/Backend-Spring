package tn.esprit.spring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.PresenceRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;

import java.util.List;


@Service
public class PresenceServiceImpl implements IPresenceService{
    private static final Logger logger = LoggerFactory.getLogger(PresenceServiceImpl.class);



    private PresenceRepository presenceRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    private final UserService userService;

    @Autowired
    public PresenceServiceImpl(PresenceRepository presenceRepository,UserService userService) {
        this.presenceRepository=presenceRepository;
        this.userService= userService;
    }

    public List<Utilisateur> getUsersByRoleAndClassId(String role, Long classId) {
        ERole eRole = ERole.valueOf(role);
        return userService.findUsersByRoleAndClasseId(eRole, classId);
    }

    public Utilisateur getUserByRoleAndIdAndClasseId(String role,Long id, Long classeId){
        ERole eRole = ERole.valueOf(role);
        return userService.findUserByRoleAndIdAndClasseId(eRole,id,classeId);
    }

    @Override
    public Presence addPresence(Presence presence) {
        return presenceRepository.save(presence);
    }

    @Override
    public List<Presence> getAllPresences() {
        return presenceRepository.findAll();
    }

    public Presence addPresenceForEtudiant(Long studentId, Presence presenceDetails) {
        Utilisateur student = utilisateurRepository.findById(studentId).orElse(null);
        if (student != null) {
            logger.info("Found student with ID: {}", studentId);
            presenceDetails.setUtilisateur(student);
            return presenceRepository.save((presenceDetails));
        } else {
            throw new RuntimeException("Student with ROLE_ETUDIANT not found with ID: " + studentId);
        }
    }




}
