package tn.esprit.spring.services;

import com.opencsv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.Dto.ClasseDTO1;
import tn.esprit.spring.Dto.ConversionUtil;
import tn.esprit.spring.Dto.NoteDTO;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.NoteRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.repositories.MatiereRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;
import org.springframework.web.multipart.MultipartFile;
import java.io.OutputStream;
@Service
public class NoteServicesImpl implements INoteServices {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private  ClasseRepository classeRepository;

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
            if (noteDTO.getNotePrincipale() != null) {
                note.setNotePrincipale(noteDTO.getNotePrincipale());
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


    public ClasseDTO1 getClassByUserId(Long userId) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        Classe classe = user.getClasse();
        return ConversionUtil.convertToClasseDTO(classe);
    }
    public void generateNotesPdf(Long userId, OutputStream outputStream) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(userId);
        if (utilisateurOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        Utilisateur utilisateur = utilisateurOpt.get();
        List<Note> notes = noteRepository.findByUtilisateurId(userId);
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            document.add(new Paragraph("Relevé de Notes"));
            document.add(new Paragraph("Nom et Prénom: " + utilisateur.getNom() + " " + utilisateur.getPrenom()));
            if (utilisateur.getClasse() != null) {
                document.add(new Paragraph("Classe: " + utilisateur.getClasse().getNomClasse()));
            }

            PdfPTable table = new PdfPTable(4);
            table.addCell("Matiere");
            table.addCell("Note TP");
            table.addCell("Note CC");
            table.addCell("Note Examen");

            for (Note note : notes) {
                table.addCell(note.getMatiere().getNomMatiere());
                table.addCell(note.getNoteTp() != null ? note.getNoteTp().toString() : "-");
                table.addCell(note.getNoteCc() != null ? note.getNoteCc().toString() : "-");
                table.addCell(note.getNoteExamen() != null ? note.getNoteExamen().toString() : "-");
            }

            document.add(table);
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF", e);
        } finally {
            document.close();
        }
    }

    public void calculateAndSaveAveragesForUser(Long userId) {
        List<Note> notes = noteRepository.findByUtilisateurId(userId);

        for (Note note : notes) {
            double tp = note.getNoteTp() != null ? note.getNoteTp() : 0;
            double cc = note.getNoteCc() != null ? note.getNoteCc() : 0;
            double examen = note.getNoteExamen() != null ? note.getNoteExamen() : 0;
            double average = (tp + cc + examen) / 3;
            note.setNotePrincipale(average);
        }

        noteRepository.saveAll(notes);
    }
    public Double calculateOverallAverage(Long studentId) {
        List<Note> notes = noteRepository.findByUtilisateurId(studentId);
        Map<Long, List<Note>> notesByMatiere = notes.stream()
                .collect(Collectors.groupingBy(note -> note.getMatiere().getId()));

        double totalSum = 0.0;
        double totalCoefficient = 0.0;

        for (Map.Entry<Long, List<Note>> entry : notesByMatiere.entrySet()) {
            List<Note> matiereNotes = entry.getValue();
            boolean allHaveNoteExamen = matiereNotes.stream().allMatch(note -> note.getNoteExamen() != null);
            if (allHaveNoteExamen) {
                double average = matiereNotes.stream()
                        .mapToDouble(note -> note.getNoteTp() * note.getMatiere().getCoefficientTP() +
                                note.getNoteCc() * note.getMatiere().getCoefficientCC() +
                                note.getNoteExamen() * note.getMatiere().getCoefficientExamen())
                        .average()
                        .orElse(0.0);

                double coefficient = matiereNotes.get(0).getMatiere().getCoefficient();

                totalSum += average * coefficient;
                totalCoefficient += coefficient;
            }
        }

        if (totalCoefficient == 0) {
            return null; // Or any other indication that the overall average cannot be calculated
        }

        return totalSum / totalCoefficient;
    }

}
