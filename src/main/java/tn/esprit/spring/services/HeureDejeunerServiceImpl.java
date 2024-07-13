package tn.esprit.spring.services;

import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.HeureDejeuner;
import tn.esprit.spring.repositories.HeureDejeunerRepository;

import java.util.List;

@Service
public class HeureDejeunerServiceImpl implements IHeureDejeunerService {
    private final HeureDejeunerRepository heureDejeunerRepository;

    public HeureDejeunerServiceImpl(HeureDejeunerRepository heureDejeunerRepository) {
        this.heureDejeunerRepository = heureDejeunerRepository;
    }
    @Override
    public List<HeureDejeuner> getAllHeuresDejeuner() {
        return heureDejeunerRepository.findAll();
    }


    @Override
    public HeureDejeuner addHeureDejeuner(HeureDejeuner heureDejeuner) {
        return heureDejeunerRepository.save(heureDejeuner);
    }

    @Override
    public HeureDejeuner updateHeureDejeuner(Long id, HeureDejeuner heureDejeuner) {
        HeureDejeuner existingHeureDejeuner = heureDejeunerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeureDejeuner not found"));
        existingHeureDejeuner.setDuHeure(heureDejeuner.getDuHeure());
        existingHeureDejeuner.setAuHeure(heureDejeuner.getAuHeure());
        return heureDejeunerRepository.save(existingHeureDejeuner);
    }
}
