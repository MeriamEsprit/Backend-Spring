package tn.esprit.spring.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.JourFerie;
import tn.esprit.spring.repositories.JourFerieRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;


import java.util.List;

@Service
public class JourFerieServiceImpl implements IJourFerieService {

    @Autowired
    private UserService userService;

    private final JourFerieRepository jourFerieRepository;

    public JourFerieServiceImpl(JourFerieRepository jourFerieRepository) {
        this.jourFerieRepository = jourFerieRepository;
    }

    @Override
    public List<JourFerie> getAllJourFeries() {



        return jourFerieRepository.findAll();
    }

    @Override
    public JourFerie addJourFerie(JourFerie jourFerie) {
        return jourFerieRepository.save(jourFerie);
    }

    @Override
    public JourFerie updateJourFerie(Long id, JourFerie jourFerie) {
        JourFerie existingJourFerie = jourFerieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JourFerie not found"));
        existingJourFerie.setDate(jourFerie.getDate());
        existingJourFerie.setNom(jourFerie.getNom());
        return jourFerieRepository.save(existingJourFerie);
    }
}
