package tn.esprit.spring.services;

import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPresenceService {

    Presence addPresence (Presence presence);
    List<Presence> getAllPresences();
    Presence updatePresence(Presence presence);
    Presence getPresenceById(Long id);

    Presence getPresenceByIdAndDate(Long idPresence, LocalDate date);

    Presence addPresenceForEtudiant(Long studentId, Presence presenceDetails);
    Presence updatePresenceForEtudiant(Long studentId, Presence presenceDetails);
    void deletePresence(Long id);

    List<Presence> getAllPresencesByUserId(Long id);

    Map<String, List<Presence>> getMonthlyAttendance(Long userId, int month, int year);

    List<Presence> getPresencesByClasseId(Long classeId);

    List<Utilisateur> findStudentByClasse(Long classId);

    boolean checkPresenceExists(Long studentId, LocalDate datePresence);

    Long getCountByEtatPresenceTrue();

    Long getCountByEtatPresenceFalse();

    Long getCountByEtatPresenceTrueAndClasseId(Long classeId);

    Long getCountByEtatPresenceFalseAndClasseId(Long classeId);

    Long getCountByEtatPresenceTrueAndMatiereId(Long matiereId);

    Long getCountByEtatPresenceFalseAndMatiereId(Long matiereId);
}
