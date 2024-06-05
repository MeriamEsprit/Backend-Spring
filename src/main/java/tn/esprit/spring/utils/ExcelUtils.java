package tn.esprit.spring.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    public static List<Note> parseExcelFile(InputStream is, UtilisateurRepository utilisateurRepository, MatiereRepository matiereRepository) throws IOException {
        List<Note> notes = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Note note = new Note();
            note.setNoteCc(row.getCell(0).getNumericCellValue());
            note.setNoteTp(row.getCell(1).getNumericCellValue());
            note.setNoteEx(row.getCell(2).getNumericCellValue());

            // Retrieve Utilisateur by ID
            Long utilisateurId = (long) row.getCell(3).getNumericCellValue();
            Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
            if (utilisateur == null) {
                // Handle the case where the user does not exist
                throw new IllegalArgumentException("Utilisateur with ID " + utilisateurId + " does not exist");
            }
//            note.setUtilisateur(utilisateur);

            // Retrieve Matiere by ID
            Long matiereId = (long) row.getCell(4).getNumericCellValue();
            Matiere matiere = matiereRepository.findById(matiereId).orElse(null);
            if (matiere == null) {
                // Handle the case where the matiere does not exist
                throw new IllegalArgumentException("Matiere with ID " + matiereId + " does not exist");
            }
            note.setMatiere(matiere);

            notes.add(note);
        }
        workbook.close();
        return notes;
    }
}
