package tn.esprit.spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Justification;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.services.IJustificationServices;
import tn.esprit.spring.services.IPresenceService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/justifications")
public class JustificationController {

    @Autowired
    private IJustificationServices justificationServices;
    @Autowired
    private IPresenceService presenceService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/presence/{date}/{idPresence}")
    public ResponseEntity<Justification> addJustification(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                          @PathVariable Long idPresence,
                                                          @RequestParam("justification") String justificationDetails,
                                                          @RequestParam("file") MultipartFile file) {
        Presence presence = presenceService.getPresenceByIdAndDate(idPresence, date);
        if (presence == null) {
            return ResponseEntity.notFound().build();
        }

        Justification justification = new Justification();
        justification.setReason(justificationDetails); // Set the justification reason directly
        justification.setPresences(new ArrayList<>());
        justification.getPresences().add(presence);

        String filePath = saveFile(file, justification.getIdJustification());
        justification.setFilePath(filePath);

        Justification createdJustification = justificationServices.addJustification(justification);
        presence.setJustification(createdJustification);
        presenceService.updatePresence(presence);

        return ResponseEntity.ok(createdJustification);
    }

    @PutMapping("/updateJustification/{id}")
    public ResponseEntity<Justification> updateJustification(@PathVariable Long id, @RequestBody Justification justification) {
        justification.setIdJustification(id);
        Justification updatedJustification = justificationServices.updateJustificationById(justification, id);
        return ResponseEntity.ok(updatedJustification);
    }

    @GetMapping("/getJustification/{id}")
    public ResponseEntity<Justification> getJustificationById(@PathVariable Long id) {
        Justification justification = justificationServices.getJustificationById(id);
        return ResponseEntity.ok(justification);
    }

    @DeleteMapping("/deleteJustification/{id}")
    public ResponseEntity<Void> deleteJustification(@PathVariable Long id) {
        justificationServices.deleteJustification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllJustifications")
    public ResponseEntity<List<Justification>> getAllJustifications() {
        List<Justification> justifications = justificationServices.getAllJustification();
        return ResponseEntity.ok(justifications);
    }

    private Justification parseJustificationDetails(String justificationDetails) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(justificationDetails, Justification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse justification details", e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }
    private String saveFile(MultipartFile file, Long justificationId) {
        String fileName = "justification_" + justificationId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        try {
            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath.toFile());
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

}
