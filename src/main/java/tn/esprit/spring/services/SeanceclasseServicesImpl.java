package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.SeanceClasse;
import tn.esprit.spring.repositories.SeanceclasseRepository;

import java.util.List;

@Service
public class SeanceclasseServicesImpl implements ISeanceclasseServices {

    @Autowired
    private SeanceclasseRepository seanceclasseRepository;

    @Override
    public SeanceClasse saveSeanceclasse(SeanceClasse seanceclasse) {
        return seanceclasseRepository.save(seanceclasse);
    }

    @Override
    public SeanceClasse updateSeanceclasse(SeanceClasse seanceclasse) {
        return seanceclasseRepository.save(seanceclasse);
    }

    @Override
    public void deleteSeanceclasse(Long id) {
        seanceclasseRepository.deleteById(id);
    }

    @Override
    public SeanceClasse getSeanceclasseById(Long id) {
        return seanceclasseRepository.findById(id).orElse(null);
    }

    @Override
    public List<SeanceClasse> getAllSeanceclasses() {
        return seanceclasseRepository.findAll();
    }
}
