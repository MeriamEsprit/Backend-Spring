package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Dto.ConversionUtil;
import tn.esprit.spring.Dto.MatiereDTO;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.TypeMatiere;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.repositories.ModuleRepository;
import tn.esprit.spring.repositories.NoteRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatiereServicesImp implements IMatiereServices {

    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public MatiereDTO saveMatiere(MatiereDTO matiereDTO) {
        Matiere matiere;
        if (matiereDTO.getType() != null && matiereDTO.getType().equals("Projet")) {
            matiere = ConversionUtil.convertToMatiereEntity(matiereDTO, null);
        } else {
            Module module = moduleRepository.findById(matiereDTO.getModuleId())
                    .orElseThrow(() -> new IllegalArgumentException("Module not found"));
            matiere = ConversionUtil.convertToMatiereEntity(matiereDTO, module);
        }
        Matiere savedMatiere = matiereRepository.save(matiere);
        return ConversionUtil.convertToMatiereDTO(savedMatiere);
    }

    @Override
    public MatiereDTO updateMatiere(Long id, MatiereDTO matiereDTO) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matiere not found"));

        if (matiereDTO.getType() != null && matiereDTO.getType().equals("PROJET")) {
            matiere.setModule(null);
        } else {
            Module module = moduleRepository.findById(matiereDTO.getModuleId())
                    .orElseThrow(() -> new IllegalArgumentException("Module not found"));
            matiere.setModule(module);
        }

        matiere.setNomMatiere(matiereDTO.getNomMatiere());
        matiere.setNbreHeures(matiereDTO.getNbreHeures());
        matiere.setCoefficient(matiereDTO.getCoefficient());
        matiere.setCoefficientTP(matiereDTO.getCoefficientTP());
        matiere.setCoefficientCC(matiereDTO.getCoefficientCC());
        matiere.setCoefficientExamen(matiereDTO.getCoefficientExamen());
        if (matiereDTO.getType() != null) {
            matiere.setType(TypeMatiere.valueOf(matiereDTO.getType()));
        }

        Matiere updatedMatiere = matiereRepository.save(matiere);
        return ConversionUtil.convertToMatiereDTO(updatedMatiere);
    }

    @Override
    public void deleteMatiere(Long id) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matiere not found"));

        for (Classe classe : matiere.getClasses()) {
            classe.getMatieres().remove(matiere);
        }
        for (Note note : matiere.getNotes()) {
            note.setMatiere(null);
        }

        matiereRepository.delete(matiere);
    }

    @Override
    public MatiereDTO getMatiereById(Long id) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matiere not found"));
        return ConversionUtil.convertToMatiereDTO(matiere);
    }

    @Override
    public List<MatiereDTO> getAllMatieres() {
        List<Matiere> matieres = matiereRepository.findAll();
        return matieres.stream()
                .map(ConversionUtil::convertToMatiereDTO)
                .collect(Collectors.toList());
    }

    public boolean isNomMatiereUnique(String nomMatiere) {
        return !matiereRepository.existsByNomMatiere(nomMatiere);
    }

    public boolean isNomMatiereUniqueExceptCurrent(String nomMatiere, Long id) {
        Matiere existingMatiere = matiereRepository.findById(id).orElse(null);
        if (existingMatiere != null && existingMatiere.getNomMatiere().equals(nomMatiere)) {
            return true;
        }
        return !matiereRepository.existsByNomMatiere(nomMatiere);
    }
    public int countMatieresBelowTenForUser(Long userId) {
        List<Note> notes = noteRepository.findByUtilisateurId(userId);
        int count = 0;
        for (Note note : notes) {
            if (note.getNotePrincipale() != null && note.getNotePrincipale() < 10) {
                count++;
            }
        }
        return count;
    }

    public Map<String, Long> getMatieresBelowTenStatsForUser(Long userId) {
        List<Note> notes = noteRepository.findByUtilisateurId(userId);
        long belowTenCount = notes.stream()
                .filter(note -> note.getNotePrincipale() != null && note.getNotePrincipale() < 10)
                .count();
        long totalCount = notes.size();
        Map<String, Long> stats = new HashMap<>();
        stats.put("belowTenCount", belowTenCount);
        stats.put("totalCount", totalCount);
        return stats;
    }

    public long count() {
        return matiereRepository.count();
    }


    public Map<String, Double> calculateGradeDistribution() {
        List<Note> notes = noteRepository.findAll();

        // Filter out notes with null notePrincipale
        List<Note> validNotes = notes.stream()
                .filter(note -> note.getNotePrincipale() != null)
                .collect(Collectors.toList());

        long gradeA = validNotes.stream().filter(note -> note.getNotePrincipale() >= 15).count();
        long gradeB = validNotes.stream().filter(note -> note.getNotePrincipale() >= 12 && note.getNotePrincipale() < 15).count();
        long gradeC = validNotes.stream().filter(note -> note.getNotePrincipale() >= 10 && note.getNotePrincipale() < 12).count();
        long gradeD = validNotes.stream().filter(note -> note.getNotePrincipale() < 10).count();

        long total = gradeA + gradeB + gradeC + gradeD;

        Map<String, Double> gradeDistribution = new HashMap<>();
        if (total > 0) {
            gradeDistribution.put("A (15-20)", (double) gradeA / total * 100);
            gradeDistribution.put("B (12-14)", (double) gradeB / total * 100);
            gradeDistribution.put("C (10-11)", (double) gradeC / total * 100);
            gradeDistribution.put("D (0-9)", (double) gradeD / total * 100);
        } else {
            gradeDistribution.put("A (15-20)", 0.0);
            gradeDistribution.put("B (12-14)", 0.0);
            gradeDistribution.put("C (10-11)", 0.0);
            gradeDistribution.put("D (0-9)", 0.0);
        }

        return gradeDistribution;
    }




}
