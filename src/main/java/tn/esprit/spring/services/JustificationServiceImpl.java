package tn.esprit.spring.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Justification;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.repositories.JustificationRepository;
import tn.esprit.spring.repositories.PresenceRepository;

import java.util.Date;
import java.util.List;

@Service
public class JustificationServiceImpl implements IJustificationServices {

    @Autowired
    private JustificationRepository justificationRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    @Override
    public Justification addJustification(Justification justification) {
        if (justification.getSubmissionDate() == null) {
            justification.setSubmissionDate(new Date());
        }
        return justificationRepository.save(justification);
    }

    @Override
    public Justification updateJustification(Justification justification) {
        return justificationRepository.save(justification);
    }

    @Override
    @Transactional
    public Justification updateJustificationById(Justification justification, Long idJustification) {
        return justificationRepository.findById(idJustification).map(existingJustification -> {
            existingJustification.setName(justification.getName());
            existingJustification.setReason(justification.getReason());
            existingJustification.setStatus(justification.getStatus());
            existingJustification.setSubmissionDate(justification.getSubmissionDate());
            existingJustification.setValidationDate(justification.getValidationDate());
            return justificationRepository.save(existingJustification);
        }).orElseThrow(() -> new EntityNotFoundException("Justification not found with id " + idJustification));
    }

    @Override
    public Justification getJustificationById(Long idJustification) {
        return justificationRepository.findById(idJustification)
                .orElseThrow(() -> new EntityNotFoundException("Justification not found with id " + idJustification));
    }

    @Override
    public void deleteJustification(Long idJustification) {
        if (justificationRepository.existsById(idJustification)) {
            justificationRepository.deleteById(idJustification);
        } else {
            throw new EntityNotFoundException("Justification not found with id " + idJustification);
        }
    }

    @Override
    public List<Justification> getAllJustification() {
        return justificationRepository.findAll();
    }

    @Override
    @Transactional
    public Justification addJustificationByPresence(Justification justification, String date, Long idPresence) {
        Presence presence = presenceRepository.findById(idPresence)
                .orElseThrow(() -> new EntityNotFoundException("Presence not found with id " + idPresence));

        presence.setJustification(justification);
        justification.getPresences().add(presence);

        return justificationRepository.save(justification);
    }

    @Override
    public Justification validateJustification(Long id) {
        Justification justification = getJustificationById(id);
        justification.setStatus(1);  // Set status to validated
        justification.setValidationDate(new Date()); // Set validation date
        return updateJustification(justification);
    }
    @Override
    public Justification declineJustification(Long id) {
        Justification justification = getJustificationById(id);
        justification.setStatus(0);  // Set status to declined
        justification.setValidationDate(new Date()); // Set validation date
        return updateJustification(justification);
    }
}
