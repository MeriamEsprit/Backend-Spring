package tn.esprit.spring.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Justification;
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
}
