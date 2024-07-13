package tn.esprit.spring.services;

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
    @Autowired
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
    public Competence assignerCompetenceAMatiers(Long idCompetence, List<Long> idMatieres) {
        System.out.println(idMatieres);
        Competence competence = competenceRepository.findById(idCompetence).orElseThrow(() -> new RuntimeException("Competence not found"));
        List<Matiere> matieres = matiereRepository.findAllById(idMatieres);

        for (Matiere matiere : matieres) {
            matiere.setCompetence(competence);
            matiereRepository.save(matiere);
        }

        return competence;
    }
}


