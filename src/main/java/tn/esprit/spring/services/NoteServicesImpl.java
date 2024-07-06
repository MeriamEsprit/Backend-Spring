package tn.esprit.spring.services;

import com.opencsv.CSVParser;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;
import org.springframework.web.multipart.MultipartFile;
@Service
public class NoteServicesImpl implements INoteServices {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MatiereRepository matiereRepository;
    private static final Logger logger = LoggerFactory.getLogger(NoteServicesImpl.class);


    @Override
    public NoteDTO saveNote(NoteDTO noteDTO) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(noteDTO.getUtilisateurId());
        if (utilisateur.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur does not exist");
        }

        Optional<Matiere> matiere = matiereRepository.findById(noteDTO.getMatiereId());
        if (matiere.isEmpty()) {
            throw new IllegalArgumentException("Matiere does not exist");
        }

        Note note = ConversionUtil.convertToNoteEntity(noteDTO, utilisateur.get(), matiere.get());
        Note savedNote = noteRepository.save(note);
        return ConversionUtil.convertToNoteDTO(savedNote);
    }

    @Override
    public List<NoteDTO> updateNotes(List<NoteDTO> noteDTOs, Long userId) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userId);
        if (utilisateur.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        List<Note> updatedNotes = new ArrayList<>();
        for (NoteDTO noteDTO : noteDTOs) {
            Optional<Matiere> matiere = matiereRepository.findById(noteDTO.getMatiereId());
            if (matiere.isEmpty()) {
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
                note.setMatiere(matiere.get());
            }

            if (noteDTO.getNoteTp() != null) {
                note.setNoteTp(noteDTO.getNoteTp());
            }
            if (noteDTO.getNoteCc() != null) {
                note.setNoteCc(noteDTO.getNoteCc());
            }
            if (noteDTO.getNoteExamen() != null) {
                note.setNoteExamen(noteDTO.getNoteExamen());
            }

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
    public void saveNotesFromCSV(MultipartFile file, Long classeId) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> records = reader.readAll();
            if (records.size() > 0) {
                records.remove(0); // Remove header row
            }
            for (String[] record : records) {
                Long utilisateurId = Long.parseLong(record[0]);
                // Skip the student name field
                String nomMatiere = record[2];
                Double noteTp = record[3].isEmpty() ? null : Double.parseDouble(record[3]);
                Double noteCc = record[4].isEmpty() ? null : Double.parseDouble(record[4]);
                Double noteExamen = record[5].isEmpty() ? null : Double.parseDouble(record[5]);

                Optional<Utilisateur> utilisateur = utilisateurRepository.findById(utilisateurId);
                if (utilisateur.isEmpty()) {
                    throw new IllegalArgumentException("Utilisateur with ID " + utilisateurId + " does not exist");
                }

                Optional<Matiere> matiere = matiereRepository.findByNomMatiere(nomMatiere);
                if (matiere.isEmpty()) {
                    throw new IllegalArgumentException("Matiere with name " + nomMatiere + " does not exist");
                }

                Note note = noteRepository.findByUtilisateurIdAndMatiereId(utilisateurId, matiere.get().getId())
                        .orElse(new Note());

                note.setUtilisateur(utilisateur.get());
                note.setMatiere(matiere.get());

                if (noteTp != null) {
                    note.setNoteTp(noteTp);
                }
                if (noteCc != null) {
                    note.setNoteCc(noteCc);
                }
                if (noteExamen != null) {
                    note.setNoteExamen(noteExamen);
                }

                noteRepository.save(note);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file", e);
        }
    }


}
