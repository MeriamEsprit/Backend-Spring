package tn.esprit.spring.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import tn.esprit.spring.Dto.NoteDTO;
import tn.esprit.spring.services.NoteServicesImpl;

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
}
