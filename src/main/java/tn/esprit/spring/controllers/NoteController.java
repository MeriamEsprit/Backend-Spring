package tn.esprit.spring.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.repositories.NoteRepository;
import tn.esprit.spring.services.NoteServicesImpl;

import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/api/notes")
public class NoteController {
    private NoteServicesImpl noteService;
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        try {
            logger.info("Attempting to create note: {}", note);
            Note createdNote = noteService.saveNote(note);
            return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating note: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error creating note: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public Note updateNote(@RequestBody Note note, @RequestParam Long userId) {
        return noteService.updateNote(note, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id, @RequestParam Long userId) {
        noteService.deleteNote(id, userId);
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id, @RequestParam Long userId) {
        return noteService.getNoteById(id, userId);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @Autowired
    NoteRepository noteRepository;
    @GetMapping("/test")
    public List<Note> getAllNotesz() {
        return noteRepository.findAll();
    }


    @GetMapping("/user/{userId}")
    public List<Note> getNotesByUserId(@PathVariable Long userId) {
        return noteService.getNotesByUserId(userId);
    }

}
