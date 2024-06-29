package tn.esprit.spring.controllers;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.Dto.JustificationDTO;
import tn.esprit.spring.entities.Justification;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.repositories.PresenceRepository;
import tn.esprit.spring.services.IJustificationServices;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/justifications")
@RequiredArgsConstructor
public class JustificationController {

    @Autowired
    private final IJustificationServices justificationService;
    @Autowired
    private final PresenceRepository presenceRepository;

    @PostMapping("/addJustification")
    public JustificationDTO addJustification(@RequestBody JustificationDTO justificationDTO) {
        Justification justification = JustificationDTO.mapToEntity(justificationDTO, presenceRepository);
        Justification savedJustification = justificationService.addJustification(justification);
        return JustificationDTO.mapToDTO(savedJustification);
    }

    @GetMapping("/getAllJustifications")
    public List<JustificationDTO> findAllJustifications() {
        List<Justification> justifications = justificationService.getAllJustification();
        return justifications.stream().map(JustificationDTO::mapToDTO).collect(Collectors.toList());
    }

    @PutMapping("/updateJustification")
    public JustificationDTO updateJustification(@RequestBody JustificationDTO justificationDTO) {
        Justification justification = JustificationDTO.mapToEntity(justificationDTO, presenceRepository);
        Justification updatedJustification = justificationService.updateJustification(justification);
        return JustificationDTO.mapToDTO(updatedJustification);
    }

    @DeleteMapping("/deleteJustification/{id}")
    public void deleteJustification(@PathVariable Long id) {
        justificationService.deleteJustification(id);
    }

    @GetMapping("/getJustificationById/{id}")
    public JustificationDTO getJustificationById(@PathVariable Long id) {
        Justification justification = justificationService.getJustificationById(id);
        return JustificationDTO.mapToDTO(justification);
    }

    @PutMapping("/updateJustificationById/{id}")
    public JustificationDTO updateJustificationById(@PathVariable Long id, @RequestBody JustificationDTO justificationDTO) {
        Justification justification = JustificationDTO.mapToEntity(justificationDTO, presenceRepository);
        Justification updatedJustification = justificationService.updateJustificationById(justification, id);
        return JustificationDTO.mapToDTO(updatedJustification);
    }

    @PostMapping("/addJustificationByPresence/{date}/{idPresence}")
    public ResponseEntity<?> addJustificationByPresence(
            @PathVariable String date,
            @PathVariable Long idPresence,
            @RequestParam("name") String name,
            @RequestParam("reason") String reason,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("submissionDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date submissionDate) {

        // Check if a justification already exists for the given presence
        Presence presence = presenceRepository.findById(idPresence)
                .orElseThrow(() -> new EntityNotFoundException("Presence not found with id " + idPresence));

        if (presence.getJustification() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Justification already exists for this presence");
        }

        Justification justification = new Justification();
        justification.setName(name);
        justification.setReason(reason);
        justification.setSubmissionDate(submissionDate);

        if (file != null && !file.isEmpty()) {
            String filePath = saveFile(file); // Method to save the file and return the path
            justification.setFilePath(filePath);
        }

        Justification savedJustification = justificationService.addJustificationByPresence(justification, date, idPresence);
        presence.setJustification(savedJustification);
        presenceRepository.save(presence);
        Long userId = presence.getUtilisateur().getId();
        JustificationDTO justificationDTO = JustificationDTO.mapToDTO(savedJustification);
        justificationDTO.setUserId(userId);

        return ResponseEntity.ok(justificationDTO);
    }

    @PutMapping("/validate/{id}")
    public ResponseEntity<JustificationDTO> validateJustification(@PathVariable Long id) {
        Justification justification = justificationService.getJustificationById(id);
        if (justification == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        justification.setStatus(1);  // Set status to validated (1)
        justification.setValidationDate(new Date()); // Set validation date
        justificationService.updateJustification(justification);
        return ResponseEntity.ok(JustificationDTO.mapToDTO(justification));
    }

    @PutMapping("/decline/{id}")
    public ResponseEntity<JustificationDTO> declineJustification(@PathVariable Long id) {
        Justification justification = justificationService.getJustificationById(id);
        if (justification == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        justification.setStatus(0);  // Set status to declined (0)
        justification.setValidationDate(new Date()); // Set validation date
        justificationService.updateJustification(justification);
        return ResponseEntity.ok(JustificationDTO.mapToDTO(justification));
    }
    @GetMapping("/files/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        try {
            Path file = Paths.get("C:/xampp/htdocs/img/").resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = "application/pdf";  // Assuming the content type is PDF
                if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".png")) {
                    contentType = "image/jpeg";  // Assuming the content type is JPEG for images
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    private String saveFile(MultipartFile file) {
        try {
            String uploadDir = "C:/xampp/htdocs/img/";
            String originalFilename = file.getOriginalFilename();
            String filePath = uploadDir + System.currentTimeMillis() + "_" + originalFilename;
            File dest = new File(filePath);
            file.transferTo(dest);
            return dest.getName();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

}




