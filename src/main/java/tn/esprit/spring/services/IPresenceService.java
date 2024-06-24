package tn.esprit.spring.services;

import tn.esprit.spring.entities.Presence;

import java.util.List;

public interface IPresenceService {

    Presence addPresence (Presence presence);
    List<Presence> getAllPresences();
}
