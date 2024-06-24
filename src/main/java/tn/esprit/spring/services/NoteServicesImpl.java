package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Dto.ConversionUtil;
import tn.esprit.spring.Dto.NoteDTO;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.repositories.NoteRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.repositories.MatiereRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteServicesImpl implements INoteServices {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Override
    public NoteDTO saveNote(NoteDTO noteDTO) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(noteDTO.getUtilisateurId());
        if (utilisateur.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur does not exist");
        }

        Matiere matiere = noteDTO.getMatiere();
        if (matiere == null || matiereRepository.findById(matiere.getId()).isEmpty()) {
            throw new IllegalArgumentException("Matiere does not exist");
        }

        Note note = ConversionUtil.convertToNoteEntity(noteDTO, utilisateur.get(), matiere);
        Note savedNote = noteRepository.save(note);
        return ConversionUtil.convertToNoteDTO(savedNote);
    }

    public List<NoteDTO> updateNotes(List<NoteDTO> noteDTOs, Long userId) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userId);
        if (utilisateur.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        List<Note> updatedNotes = new ArrayList<>();
        for (NoteDTO noteDTO : noteDTOs) {
            Matiere matiere = noteDTO.getMatiere();
            if (matiere == null || matiereRepository.findById(matiere.getId()).isEmpty()) {
                throw new IllegalArgumentException("Matiere does not exist");
            }

            Note note;
            if (noteDTO.getId() != null) {
                Optional<Note> existingNoteOptional = noteRepository.findById(noteDTO.getId());
                if (existingNoteOptional.isEmpty()) {
                    throw new IllegalArgumentException("Note does not exist");
                }
                note = existingNoteOptional.get();
            } else {
                note = new Note();
                note.setUtilisateur(utilisateur.get());
                note.setMatiere(matiere);
            }

            note.setNoteTp(noteDTO.getNoteTp());
            note.setNoteCc(noteDTO.getNoteCc());
            note.setNoteExamen(noteDTO.getNoteExamen());

            Note updatedNote = noteRepository.save(note);
            updatedNotes.add(updatedNote);
        }

        return updatedNotes.stream().map(ConversionUtil::convertToNoteDTO).collect(Collectors.toList());
    }


    @Override
    public void deleteNote(Long id, Long userId) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userId);
        if (utilisateur.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        noteRepository.deleteById(id);
    }

    @Override
    public NoteDTO getNoteById(Long id, Long userId) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userId);
        if (utilisateur.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        Note note = noteRepository.findById(id).orElse(null);
        return note != null ? ConversionUtil.convertToNoteDTO(note) : null;
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream()
                .map(ConversionUtil::convertToNoteDTO)
                .collect(Collectors.toList());
    }

    public List<NoteDTO> getNotesByUserId(Long userId) {
        List<Note> notes = noteRepository.findByUtilisateurId(userId);
        return notes.stream()
                .map(ConversionUtil::convertToNoteDTO)
                .collect(Collectors.toList());
    }


}
