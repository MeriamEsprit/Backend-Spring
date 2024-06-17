package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import tn.esprit.spring.entities.ModeDePaiement;
import tn.esprit.spring.entities.Reglement;
import tn.esprit.spring.repositories.ReglementRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;

import java.util.List;

@Service
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Ajoutez cette ligne

public class ReglementService implements IReglementService{
    @Autowired
    ReglementRepository reglementRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
   public Reglement addReglmement(Reglement reglement)
    {
      return reglementRepository.save(reglement);
    }

    @Override
    public List<Reglement> getallReglement() {
       return reglementRepository.findAll();
        // return null;
    }

    @Override
    public void removeReglement(Long idReglement) {
        reglementRepository.deleteById(idReglement);
    }

@Override
public Reglement updateReglmement(Long id,Reglement reglement) {

    Reglement existingreglement = reglementRepository.findById(id).orElse(null);
    if (existingreglement != null) {
        existingreglement.setTranche(reglement.getTranche());
        existingreglement.setModeDePaiement(reglement.getModeDePaiement());


        reglementRepository.save(existingreglement);
    }
    return null;
}

/*
    @Override
    public Reglement addReglementAndAssignReglementToUser(String email, Reglement reglement) {
        Utilisateur user = utilisateurRepository.getUtilisateurByEmail(email);
        reglement.setUtilisateur(user);
        utilisateurRepository.save(user);
        return reglementRepository.save(reglement);

    }

    @Override
    public List<Reglement> getReglementAffectesByEmail(String email) {
        Utilisateur user = utilisateurRepository.getUtilisateurByEmail(email);
        if (user != null) {
            return new ArrayList<>(user.getReglements());
        } else {
            // Gérer le cas où l'utilisateur n'existe pas
            return new ArrayList<>();
        }
    }*/

    @Override
    public List<Reglement> findbymode(ModeDePaiement modeDePaiement)
    {
        return reglementRepository.findReglementByModeDePaiement(modeDePaiement);
    }
}
