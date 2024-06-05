package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Competence;
import tn.esprit.spring.repositories.CompetenceRepository;

import java.util.List;

@Service
public class CompetenceServicesImpl implements ICompetenceServices {

    @Autowired
    private CompetenceRepository competenceRepository;

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
}


