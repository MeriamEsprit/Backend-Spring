package tn.esprit.spring.Dto;

import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.entities.Utilisateur;

import java.util.List;
import java.util.stream.Collectors;

public class ConversionUtil {
    public static ModuleDTO convertToModuleDTO(Module module) {
        ModuleDTO dto = new ModuleDTO();
        dto.setId(module.getId());
        dto.setNom(module.getNom());
        dto.setDescription(module.getDescription());
        return dto;
    }

    public static Module convertToModuleEntity(ModuleDTO dto) {
        Module module = new Module();
        module.setNom(dto.getNom());
        module.setDescription(dto.getDescription());
        return module;
    }

    public static MatiereDTO convertToMatiereDTO(Matiere matiere) {
        MatiereDTO dto = new MatiereDTO();
        dto.setId(matiere.getId());
        dto.setNomMatiere(matiere.getNomMatiere());
        dto.setNbreHeures(matiere.getNbreHeures());
        dto.setCoefficientTP(matiere.getCoefficientTP());
        dto.setCoefficientCC(matiere.getCoefficientCC());
        dto.setCoefficientExamen(matiere.getCoefficientExamen());
        dto.setModuleId(matiere.getModule() != null ? matiere.getModule().getId() : null);
        return dto;
    }

    public static Matiere convertToMatiereEntity(MatiereDTO dto, Module module) {
        Matiere matiere = new Matiere();
        matiere.setNomMatiere(dto.getNomMatiere());
        matiere.setNbreHeures(dto.getNbreHeures());
        matiere.setCoefficientTP(dto.getCoefficientTP());
        matiere.setCoefficientCC(dto.getCoefficientCC());
        matiere.setCoefficientExamen(dto.getCoefficientExamen());
        matiere.setModule(module);
        return matiere;
    }

    public static NoteDTO convertToNoteDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setNoteCc(note.getNoteCc());
        dto.setNoteTp(note.getNoteTp());
        dto.setNoteExamen(note.getNoteExamen());
        dto.setNotePrincipale(note.getNotePrincipale());
        dto.setNoteControle(note.getNoteControle());
        dto.setMoyennePrincipale(note.getMoyennePrincipale());
        dto.setMoyenneControle(note.getMoyenneControle());
        dto.setUtilisateurId(note.getUtilisateur() != null ? note.getUtilisateur().getId() : null);
        dto.setMatiereId(note.getMatiere() != null ? note.getMatiere().getId() : null); // Ensure matiereId is set
        System.out.println("Converted Note: " + note.getId() + ", Matiere ID: " + note.getMatiere().getId());


        return dto;
    }

    public static Note convertToNoteEntity(NoteDTO dto, Utilisateur utilisateur, Matiere matiere) {
        Note note = new Note();
        note.setNoteCc(dto.getNoteCc());
        note.setNoteTp(dto.getNoteTp());
        note.setNoteExamen(dto.getNoteExamen());
        note.setNotePrincipale(dto.getNotePrincipale());
        note.setNoteControle(dto.getNoteControle());
        note.setMoyennePrincipale(dto.getMoyennePrincipale());
        note.setMoyenneControle(dto.getMoyenneControle());
        note.setUtilisateur(utilisateur);
        note.setMatiere(matiere);
        return note;
    }
}
