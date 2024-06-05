package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Disponibilite;
import tn.esprit.spring.repositories.DisponibiliteRepository;

import java.util.List;

@Service
public class DisponibiliteServicesImpl implements IDisponibiliteServices {

    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

    @Override
    public Disponibilite saveDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }

    @Override
    public Disponibilite updateDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }

    @Override
    public void deleteDisponibilite(Long id) {
        disponibiliteRepository.deleteById(id);
    }

    @Override
    public Disponibilite getDisponibiliteById(Long id) {
        return disponibiliteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Disponibilite> getAllDisponibilites() {
        return disponibiliteRepository.findAll();
    }
}
