package tn.esprit.spring.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.Dto.NoteDTO;
import tn.esprit.spring.entities.Note;

import java.util.List;

public interface INoteServices {

    NoteDTO saveNote(NoteDTO note);
    List<NoteDTO> updateNotes(List<NoteDTO> note, Long userId);

    void deleteNote(Long id, Long userId);
    NoteDTO getNoteById(Long id, Long userId);
    List<NoteDTO> getAllNotes();
    List<NoteDTO> getNotesByUserId(Long userId);


}
