package tn.esprit.spring.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.JustificationDTO;
import tn.esprit.spring.entities.Justification;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.repositories.PresenceRepository;
import tn.esprit.spring.services.IJustificationServices;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/justifications")
@RequiredArgsConstructor
public class JustificationController {

    @Autowired
    private final IJustificationServices justificationService;
    @Autowired
    private PresenceRepository presenceRepository;

    @PostMapping("/addJustification")
    public JustificationDTO addJustification(@RequestBody JustificationDTO justificationDTO) {
        Justification justification = mapToEntity(justificationDTO);
        Justification savedJustification = justificationService.addJustification(justification);
        return mapToDTO(savedJustification);
    }

    @GetMapping("/getAllJustifications")
    public List<JustificationDTO> findAllJustifications() {
        List<Justification> justifications = justificationService.getAllJustification();
        return justifications.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @PutMapping("/updateJustification")
    public JustificationDTO updateJustification(@RequestBody JustificationDTO justificationDTO) {
        Justification justification = mapToEntity(justificationDTO);
        Justification updatedJustification = justificationService.updateJustification(justification);
        return mapToDTO(updatedJustification);
    }

    @DeleteMapping("/deleteJustification/{id}")
    public void deleteJustification(@PathVariable Long id) {
        justificationService.deleteJustification(id);
    }

    @GetMapping("/getJustificationById/{id}")
    public JustificationDTO getJustificationById(@PathVariable Long id) {
        Justification justification = justificationService.getJustificationById(id);
        return mapToDTO(justification);
    }

    @PutMapping("/updateJustificationById/{id}")
    public JustificationDTO updateJustificationById(@PathVariable Long id, @RequestBody JustificationDTO justificationDTO) {
        Justification justification = mapToEntity(justificationDTO);
        Justification updatedJustification = justificationService.updateJustificationById(justification, id);
        return mapToDTO(updatedJustification);
    }

    private JustificationDTO mapToDTO(Justification justification) {
        JustificationDTO justificationDTO = new JustificationDTO();
        justificationDTO.setIdJustification(justification.getIdJustification());
        justificationDTO.setName(justification.getName());
        justificationDTO.setReason(justification.getReason());
        justificationDTO.setStatus(justification.getStatus());
        justificationDTO.setSubmissionDate(justification.getSubmissionDate());
        justificationDTO.setValidationDate(justification.getValidationDate());
        justificationDTO.setFilePath(justification.getFilePath());
        justificationDTO.setPresenceIds(justification.getPresences().stream().map(Presence::getIdPresence).collect(Collectors.toList()));
        return justificationDTO;
    }

    private Justification mapToEntity(JustificationDTO justificationDTO) {
        Justification justification = new Justification();
        justification.setIdJustification(justificationDTO.getIdJustification());
        justification.setName(justificationDTO.getName());
        justification.setReason(justificationDTO.getReason());
        justification.setStatus(justificationDTO.getStatus());
        justification.setSubmissionDate(justificationDTO.getSubmissionDate());
        justification.setValidationDate(justificationDTO.getValidationDate());
        justification.setFilePath(justificationDTO.getFilePath());
        List<Presence> presences = justificationDTO.getPresenceIds().stream()
                .map(id -> presenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Presence not found with id " + id)))
                .collect(Collectors.toList());
        justification.setPresences(presences);
        return justification;
    }
}
