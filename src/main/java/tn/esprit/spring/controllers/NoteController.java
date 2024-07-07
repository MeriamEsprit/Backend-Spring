package tn.esprit.spring.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.Dto.ClasseDTO1;
import tn.esprit.spring.Dto.NoteDTO;
import tn.esprit.spring.Dto.emploiDuTemps.ClasseDTO;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.services.NoteServicesImpl;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notes")
public class NoteController {
    private NoteServicesImpl noteService;
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO noteDTO) {
        try {
            logger.info("Attempting to create note: {}", noteDTO);
            NoteDTO createdNote = noteService.saveNote(noteDTO);
            return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating note: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error creating note: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id, @RequestParam Long userId) {
        noteService.deleteNote(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id, @RequestParam Long userId) {
        NoteDTO noteDTO = noteService.getNoteById(id, userId);
        return noteDTO != null ? new ResponseEntity<>(noteDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<NoteDTO> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteDTO>> getNotesByUser(@PathVariable Long userId) {
        List<NoteDTO> notes = noteService.getNotesByUserId(userId);
        return notes != null ? ResponseEntity.ok(notes) : ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{userId}/notes")
    public ResponseEntity<List<NoteDTO>> updateNotes(@PathVariable Long userId, @RequestBody List<NoteDTO> noteDTOs) {
        List<NoteDTO> updatedNotes = noteService.updateNotes(noteDTOs, userId);
        return ResponseEntity.ok(updatedNotes);
    }
    @PostMapping("/upload/{classeId}")
    public ResponseEntity<String> uploadNotes(@RequestParam("file") MultipartFile file, @PathVariable Long classeId) {
        try {
            noteService.saveNotesFromCSV(file, classeId);
            return new ResponseEntity<>("Notes uploaded successfully.", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error uploading notes: {}", e.getMessage());
            return new ResponseEntity<>("Failed to upload notes.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/class")
    public ResponseEntity<ClasseDTO1> getClassByUserId(@PathVariable Long userId) {
        try {
            ClasseDTO1 classe = noteService.getClassByUserId(userId);
            return new ResponseEntity<>(classe, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching class for user {}: {}", userId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error fetching class for user {}: {}", userId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/download/{userId}")
    public void downloadNotes(@PathVariable Long userId, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"notes.pdf\"");
            noteService.generateNotesPdf(userId, response.getOutputStream());
        } catch (IOException e) {
            logger.error("Error generating PDF for user {}: {}", userId, e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
