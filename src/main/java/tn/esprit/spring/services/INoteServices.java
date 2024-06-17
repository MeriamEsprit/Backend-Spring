package tn.esprit.spring.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Note;

import java.util.List;

public interface INoteServices {

    Note saveNote(Note note, Long userId);
    Note updateNote(Note note, Long userId);
    void deleteNote(Long id, Long userId);
    Note getNoteById(Long id, Long userId);
    List<Note> getAllNotes(Long userId);
    void saveNotesFromExcel(MultipartFile file, Long userId);
    List<Note> getNotesByUserId(Long userId);


}
