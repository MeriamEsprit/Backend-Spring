package tn.esprit.spring.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Competence;
import tn.esprit.spring.repositories.CompetenceRepository;
import tn.esprit.spring.entities.Matiere;
import tn.esprit.spring.repositories.MatiereRepository;

import java.util.List;

@Service
public class CompetenceServicesImpl implements ICompetenceServices {

    @Autowired
    private CompetenceRepository competenceRepository;
    private MatiereRepository matiereRepository;

    @Override
    public Competence saveCompetence(Competence competence) {
        return competenceRepository.save(competence);
    }


    @Override
    public Competence updateCompetence(Competence competence) {
        return competenceRepository.save(competence);
    }

    @Override
    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);
    }

    @Override
    public Competence getCompetenceById(Long id) {
        return competenceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }
    @Override
    public void assignCompetenceToMatieres(Long competenceId, List<Long> matiereIds) {
        Competence competence = competenceRepository.findById(competenceId)
                .orElseThrow(() -> new EntityNotFoundException("Competence not found"));

        List<Matiere> matieres = matiereRepository.findAllById(matiereIds);
        for (Matiere matiere : matieres) {
            matiere.setCompetence(competence);
        }
        matiereRepository.saveAll(matieres);
    }
}


