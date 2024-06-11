package tn.esprit.spring.services;

import tn.esprit.spring.entities.ModeDePaiement;
import tn.esprit.spring.entities.Reglement;

import java.util.List;

public interface IReglementService {
    Reglement addReglmement(Reglement reglement);
    List<Reglement> getallReglement();
    void removeReglement ( Long idReglement);

    Reglement updateReglmement(Long id, Reglement reglement);

    List<Reglement> findbymode(ModeDePaiement modeDePaiement);


    //   Reglement addReglementAndAssignReglementToUser(String email, Reglement reglement);

   // List<Reglement> getReglementAffectesByEmail(String email);
}
