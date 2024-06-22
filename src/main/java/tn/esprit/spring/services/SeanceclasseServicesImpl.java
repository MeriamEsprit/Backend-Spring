package tn.esprit.spring.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.SeanceclasseRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;
import tn.esprit.spring.repositories.SalleRepository;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.MatiereRepository;
import tn.esprit.spring.Dto.emploiDuTemps.SeanceClasseDTO;

import java.util.List;
//import java.util.stream.Collectors;


@Service
public class SeanceclasseServicesImpl implements ISeanceclasseServices {

    @Autowired
    private SeanceclasseRepository seanceClasseRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private MatiereRepository matiereRepository;


    @Override
    public SeanceClasse saveSeanceclasse(SeanceClasse seanceClasse) {
        return seanceClasseRepository.save(seanceClasse);
    }

    @Override
    public SeanceClasse addSeanceClasse(SeanceClasseDTO dto) {
        Utilisateur enseignant = utilisateurRepository.findById(dto.getEnseignant().getIdEnseignant())
                .orElseThrow(() -> new RuntimeException("Enseignant not found"));

        Salle salle = salleRepository.findById(dto.getSalle().getIdSalle())
                .orElseThrow(() -> new RuntimeException("Salle not found"));

        Classe classe = classeRepository.findById(dto.getClasse().getIdClasse())
                .orElseThrow(() -> new RuntimeException("Classe not found"));

        Matiere matiere = matiereRepository.findById(dto.getMatiere().getIdMatiere())
                .orElseThrow(() -> new RuntimeException("Matiere not found"));

        SeanceClasse seanceClasse = new SeanceClasse();
        seanceClasse.setHeureDebut(dto.getHeureDebut());
        seanceClasse.setHeureFin(dto.getHeureFin());
        seanceClasse.setEnseignant(enseignant);
        seanceClasse.setSalle(salle);
        seanceClasse.setClasse(classe);
        seanceClasse.setMatiere(matiere);

        return seanceClasseRepository.save(seanceClasse);
    }

    @Override
    public SeanceClasse updateSeanceClasse(Long idSeance, SeanceClasseDTO dto) {
        SeanceClasse seanceClasse = seanceClasseRepository.findById(idSeance)
                .orElseThrow(() -> new RuntimeException("SeanceClasse not found"));

        Utilisateur enseignant = utilisateurRepository.findById(dto.getEnseignant().getIdEnseignant())
                .orElseThrow(() -> new RuntimeException("Enseignant not found"));
        Salle salle = salleRepository.findById(dto.getSalle().getIdSalle())
                .orElseThrow(() -> new RuntimeException("Salle not found"));
        Classe classe = classeRepository.findById(dto.getClasse().getIdClasse())
                .orElseThrow(() -> new RuntimeException("Classe not found"));
        Matiere matiere = matiereRepository.findById(dto.getMatiere().getIdMatiere())
                .orElseThrow(() -> new RuntimeException("Matiere not found"));

        seanceClasse.setHeureDebut(dto.getHeureDebut());
        seanceClasse.setHeureFin(dto.getHeureFin());
        seanceClasse.setEnseignant(enseignant);
        seanceClasse.setSalle(salle);
        seanceClasse.setClasse(classe);
        seanceClasse.setMatiere(matiere);

        return seanceClasseRepository.save(seanceClasse);
    }

    @Override
    public List<SeanceClasse> getSeanceClassesByEnseignant(Long idEnseignant) {
        return seanceClasseRepository.findByEnseignantId(idEnseignant);
    }
    @Override
    public List<SeanceClasse> getSeanceClassesByClasse(Long idClasse) {
        return seanceClasseRepository.findByClasseId(idClasse);
    }

    @Override
    public void deleteSeanceclasse(Long id) {
        seanceClasseRepository.deleteById(id);
    }

    @Override
    public SeanceClasse getSeanceclasseById(Long id) {
        return seanceClasseRepository.findById(id).orElse(null);
    }

    @Override
    public List<SeanceClasse> getAllSeanceclasses() {
        return seanceClasseRepository.findAll();
    }

}

