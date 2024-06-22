package tn.esprit.spring.services;

import tn.esprit.spring.entities.Presence;

import java.time.LocalDate;
import java.util.List;

public interface IPresenceService {

    Presence addPresence (Presence presence);
    List<Presence> getAllPresences();
    Presence updatePresence(Presence presence);
    Presence getPresenceById(Long id);

    Presence getPresenceByIdAndDate(Long idPresence, LocalDate date);

    List<Presence> getAllPresencesByUserId(Long id);
}
