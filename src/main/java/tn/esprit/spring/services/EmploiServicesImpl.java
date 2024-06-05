package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Emploidutemp;
import tn.esprit.spring.repositories.EmploiRepository;

import java.util.List;

@Service
public class EmploiServicesImpl implements IEmploiServices {

    @Autowired
    private EmploiRepository emploiRepository;

    @Override
    public Emploidutemp saveEmploi(Emploidutemp Emploidutemp) {
        return emploiRepository.save(Emploidutemp);
    }

    @Override
    public Emploidutemp updateEmploi(Emploidutemp Emploidutemp) {
        return emploiRepository.save(Emploidutemp);
    }

    @Override
    public void deleteEmploidutemp(Long idEmploidutemp) {
        emploiRepository.deleteById(idEmploidutemp);
    }

    @Override
    public Emploidutemp getEmploiById(Long idEmploidutemp) {
        return emploiRepository.findById(idEmploidutemp).orElse(null);
    }

    @Override
    public List<Emploidutemp> getAllEmplois() {
        return emploiRepository.findAll();
    }
}
