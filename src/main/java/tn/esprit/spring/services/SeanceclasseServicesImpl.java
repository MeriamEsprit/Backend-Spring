package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Seanceclasse;
import tn.esprit.spring.repositories.SeanceclasseRepository;

import java.util.List;

@Service
public class SeanceclasseServicesImpl implements ISeanceclasseServices {

    @Autowired
    private SeanceclasseRepository seanceclasseRepository;

    @Override
    public Seanceclasse saveSeanceclasse(Seanceclasse seanceclasse) {
        return seanceclasseRepository.save(seanceclasse);
    }

    @Override
    public Seanceclasse updateSeanceclasse(Seanceclasse seanceclasse) {
        return seanceclasseRepository.save(seanceclasse);
    }

    @Override
    public void deleteSeanceclasse(Long id) {
        seanceclasseRepository.deleteById(id);
    }

    @Override
    public Seanceclasse getSeanceclasseById(Long id) {
        return seanceclasseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Seanceclasse> getAllSeanceclasses() {
        return seanceclasseRepository.findAll();
    }
}
