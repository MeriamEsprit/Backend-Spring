package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.entities.Presence;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.repositories.NoteRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.services.INoteServices;
import tn.esprit.spring.utils.ExcelUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServicesImpl implements INoteServices {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private UserService userService;

    @Override
    public Note saveNote(Note note) {
            // Ensure the utilisateur exists
            Optional<Utilisateur> utilisateur = utilisateurRepository.findById(note.getUtilisateur().getId());
            if (utilisateur.isEmpty()) {
                throw new IllegalArgumentException("Utilisateur does not exist");
            }

            // Ensure the matiere exists
            Optional<Matiere> matiere = matiereRepository.findById(note.getMatiere().getId());
            if (matiere.isEmpty()) {
                throw new IllegalArgumentException("Matiere does not exist");
            }

            note.setUtilisateur(utilisateur.get());
            note.setMatiere(matiere.get());
            return noteRepository.save(note);

    }

    @Override
    public Note updateNote(Note note, Long userId) {
        // Ensure the user exists
        Optional<Utilisateur> user = utilisateurRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        // Ensure the Matiere exists
        Optional<Matiere> matiere = matiereRepository.findById(note.getMatiere().getId());
        if (matiere.isEmpty()) {
            throw new IllegalArgumentException("Matiere does not exist");
        }

        note.setUtilisateur(user.get());
        note.setMatiere(matiere.get());
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long id, Long userId) {
        // Ensure the user exists
        Optional<Utilisateur> user = utilisateurRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        noteRepository.deleteById(id);
    }

    @Override
    public Note getNoteById(Long id, Long userId) {
        // Ensure the user exists
        Optional<Utilisateur> user = utilisateurRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        // Force initialization of lazy-loaded fields
     /*   notes.forEach(note -> {
            note.getUtilisateur().getId();
            note.getMatiere().getId();
        });*/
        return notes;
    }

    @Override
    public List<Note> getNotesByUserId(Long userId) {
        return noteRepository.findByUtilisateurId(userId);
    }
}
