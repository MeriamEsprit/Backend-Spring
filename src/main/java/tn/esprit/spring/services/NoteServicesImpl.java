package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Note;
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

    private void checkRole(Long userId, String requiredRole) {
        Optional<Utilisateur> user = utilisateurRepository.findById(userId);
        if (user.isEmpty() || !user.get().getRole().equals(requiredRole)) {
            throw new SecurityException("Unauthorized");
        }
    }
@Autowired
UserService userService;
    @Override
    public Note saveNote(Note note, Long userId) {
        checkRole(userId, "enseignant");
            userService.getAuthenticatedUser();
        // Ensure the Matiere exists
        Optional<Matiere> matiere = matiereRepository.findById(note.getMatiere().getId());
        if (matiere.isEmpty()) {
            throw new IllegalArgumentException("Matiere does not exist");
        }

        note.setMatiere(matiere.get());
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Note note, Long userId) {
        checkRole(userId, "enseignant");

        // Ensure the Matiere exists
        Optional<Matiere> matiere = matiereRepository.findById(note.getMatiere().getId());
        if (matiere.isEmpty()) {
            throw new IllegalArgumentException("Matiere does not exist");
        }

        note.setMatiere(matiere.get());
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long id, Long userId) {
        checkRole(userId, "admin");
        noteRepository.deleteById(id);
    }

    @Override
    public Note getNoteById(Long id, Long userId) {
        checkRole(userId, "etudiant");
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Note> getAllNotes(Long userId) {
        checkRole(userId, "admin");
        return noteRepository.findAll();
    }

    @Override
    public void saveNotesFromExcel(MultipartFile file, Long userId) {
        checkRole(userId, "enseignant");
        try {
            List<Note> notes = ExcelUtils.parseExcelFile(file.getInputStream(), utilisateurRepository, matiereRepository);
            noteRepository.saveAll(notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Note> getNotesByUserId(Long userId) {
        checkRole(userId, "etudiant");
        return noteRepository.findByUtilisateurId(userId);
    }
}
