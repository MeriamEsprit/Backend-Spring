package tn.esprit.spring.services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.repositories.PresenceRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PresenceServiceImpl implements IPresenceService{

    @Autowired
    private PresenceRepository presenceRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private final ClasseRepository classeRepository;
    private final MatiereRepository matiereRepository;

    @Autowired
    public PresenceServiceImpl(PresenceRepository presenceRepository,ClasseRepository classeRepository,
                               MatiereRepository matiereRepository) {
        this.presenceRepository=presenceRepository;
        this.classeRepository = classeRepository;
        this.matiereRepository = matiereRepository;
    }

    @Override
    public Presence addPresence(Presence presence) {
        return presenceRepository.save(presence);
    }

    @Override
    public Presence getPresenceById(Long id) {
        return presenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Presence not found with id " + id));
    }

    @Override
    public Presence getPresenceByIdAndDate(Long idPresence, LocalDate datePresence) {
        return presenceRepository.findByIdPresenceAndDatePresence(idPresence, datePresence);
    }

    @Override
    public List<Presence> getAllPresencesByUserId(Long id) {
        Utilisateur user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return user.getPresences();
    }

    @Override
    public Presence updatePresence(Presence presence) {
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

    @Override
    public void deletePresence(Long id) {
        presenceRepository.deleteById(id);
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

    public Presence updatePresenceForEtudiant(Long studentId, Presence presenceDetails) {
        Utilisateur student = utilisateurRepository.findById(studentId).orElse(null);
        if (student != null) {
            Presence existingPresence = presenceRepository.findById(presenceDetails.getIdPresence())
                    .orElseThrow(() -> new RuntimeException("Presence not found with ID: " + presenceDetails.getIdPresence()));

            // Update the existing presence details
            existingPresence.setEtatPresence(presenceDetails.getEtatPresence());
            existingPresence.setDatePresence(presenceDetails.getDatePresence());
            existingPresence.setHeureDebut(presenceDetails.getHeureDebut());
            existingPresence.setHeureFin(presenceDetails.getHeureFin());
            existingPresence.setUtilisateur(student);

            return presenceRepository.save(existingPresence);
        } else {
            throw new RuntimeException("Student with ROLE_ETUDIANT not found with ID: " + studentId);
        }
    }

    public List<Utilisateur>findStudentByClasse(Long classId)
    {
        return classeRepository.findById(classId).get().getUtilisateurs();
    }

    @Transactional(readOnly = true)
    public List<Presence> getPresencesByClasseId(Long classeId) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new RuntimeException("Classe not found"));

        return classe.getUtilisateurs().stream()
                .flatMap(utilisateur -> utilisateur.getPresences().stream())
                .collect(Collectors.toList());
    }

    public Map<String, List<Presence>> getMonthlyAttendance(Long userId, int month, int year) {
        Utilisateur student = utilisateurRepository.findById(userId).orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getPresences().stream()
                .filter(presence -> presence.getDatePresence().getMonthValue() == month && presence.getDatePresence().getYear() == year)
                .collect(Collectors.groupingBy(presence -> presence.getDatePresence().toString()));
    }

    @Override
    public boolean checkPresenceExists(Long studentId, LocalDate datePresence) {
        Optional<Presence> presence = presenceRepository.findByUtilisateurIdAndDatePresence(studentId, datePresence);
        return presence.isPresent();
    }

    @Override
    public Long getCountByEtatPresenceTrue() {
        return presenceRepository.countByEtatPresenceTrue();
    }

    @Override
    public Long getCountByEtatPresenceFalse() {
        return presenceRepository.countByEtatPresenceFalse();
    }

    @Override
    public Long getCountByEtatPresenceTrueAndClasseId(Long classeId) {
        return presenceRepository.countByEtatPresenceTrueAndClasseId(classeId);
    }

    @Override
    public Long getCountByEtatPresenceFalseAndClasseId(Long classeId) {
        return presenceRepository.countByEtatPresenceFalseAndClasseId(classeId);
    }

    @Override
    public Long getCountByEtatPresenceTrueAndMatiereId(Long matiereId) {
        return presenceRepository.countByEtatPresenceTrueAndMatiereId(matiereId);
    }

    @Override
    public Long getCountByEtatPresenceFalseAndMatiereId(Long matiereId) {
        return presenceRepository.countByEtatPresenceFalseAndMatiereId(matiereId);
    }
}

