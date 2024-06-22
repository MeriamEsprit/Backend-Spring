package tn.esprit.spring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.PresenceRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;

import java.util.List;


@Service
public class PresenceServiceImpl implements IPresenceService{
//    private static final Logger logger = LoggerFactory.getLogger(PresenceServiceImpl.class);

    @Autowired
    private PresenceRepository presenceRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    private final ClasseRepository classeRepository;

    @Autowired
    public PresenceServiceImpl(PresenceRepository presenceRepository,UserService userService,
                               ClasseRepository classeRepository) {
        this.presenceRepository=presenceRepository;
        this.classeRepository = classeRepository;
    }



    @Override
    public Presence addPresence(Presence presence) {
        return presenceRepository.save(presence);
    }



@Override
    public List<Presence> getAllPresences() {
        List<Presence> presences = presenceRepository.findAll();
        for (Presence presence : presences) {
            if (presence.getUtilisateur() != null) {
                Utilisateur utilisateur = utilisateurRepository.findById(presence.getUtilisateur().getId()).orElse(null);
                presence.setUtilisateur(utilisateur);
            }
        }
        return presences;
    }
    public Presence addPresenceForEtudiant(Long studentId, Presence presenceDetails) {
        Utilisateur student = utilisateurRepository.findById(studentId).orElse(null);
        System.out.println(student.getId());
        if (student != null) {
            presenceDetails.setUtilisateur(student);
            System.out.println(presenceDetails);
            return presenceRepository.save((presenceDetails));
        } else {
            throw new RuntimeException("Student with ROLE_ETUDIANT not found with ID: " + studentId);
        }
    }

    public List<Utilisateur>findStudentByClasse(Long classId)
    {
        return classeRepository.findById(classId).get().getUtilisateurs();
    }



}