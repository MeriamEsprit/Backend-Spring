package tn.esprit.spring.services;

import tn.esprit.spring.entities.Justification;

import java.util.List;

public interface IJustificationServices {

    Justification addJustification (Justification justification);
    Justification updateJustification (Justification justification);
    Justification updateJustificationById(Justification justification, Long idPresence);
    Justification getJustificationById(Long idJustification);
    void deleteJustification(Long idJustification);
    List<Justification>getAllJustification();
    Justification addJustificationByPresence(Justification justification, String date, Long idPresence);
    Justification validateJustification(Long idJustification);
    Justification declineJustification(Long idJustification);
}
