package tn.esprit.spring.Dto;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.entities.Module;

import java.util.stream.Collectors;

public class ConversionUtil {
    public static ModuleDTO convertToModuleDTO(Module module) {
        ModuleDTO dto = new ModuleDTO();
        dto.setId(module.getId());
        dto.setNom(module.getNom());
        dto.setDescription(module.getDescription());
        dto.setMatieres(module.getMatieres().stream().map(ConversionUtil::convertToMatiereDTO).collect(Collectors.toList())); // Add this line
        return dto;
    }

    public static Module convertToModuleEntity(ModuleDTO dto) {
        Module module = new Module();
        module.setNom(dto.getNom());
        module.setDescription(dto.getDescription());
        // Convert MatiereDTO to Matiere if needed
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
        dto.setCoefficient(matiere.getCoefficient()); // Add this line
        dto.setModuleId(matiere.getModule() != null ? matiere.getModule().getId() : null);
        dto.setType(matiere.getType() != null ? matiere.getType().name() : null); // Add this line
        return dto;
    }

    public static Matiere convertToMatiereEntity(MatiereDTO dto, Module module) {
        Matiere matiere = new Matiere();
        matiere.setNomMatiere(dto.getNomMatiere());
        matiere.setNbreHeures(dto.getNbreHeures());
        matiere.setCoefficientTP(dto.getCoefficientTP());
        matiere.setCoefficientCC(dto.getCoefficientCC());
        matiere.setCoefficientExamen(dto.getCoefficientExamen());
        matiere.setCoefficient(dto.getCoefficient()); // Add this line
        matiere.setModule(module);
        if (dto.getType() != null) {
            matiere.setType(TypeMatiere.valueOf(dto.getType())); // Add this line
        }
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

    public static ClasseDTO1 convertToClasseDTO(Classe classe) {
        ClasseDTO1 dto = new ClasseDTO1();
        dto.setId(classe.getId());
        dto.setNomClasse(classe.getNomClasse());
        return dto;
    }

    public static Classe convertToClasseEntity(ClasseDTO1 dto) {
        Classe classe = new Classe();
        classe.setNomClasse(dto.getNomClasse());
        return classe;
    }
}
