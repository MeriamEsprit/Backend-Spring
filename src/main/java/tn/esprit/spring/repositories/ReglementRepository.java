package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.ModeDePaiement;
import tn.esprit.spring.entities.Reglement;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Tranche;

import java.util.List;

public interface ReglementRepository extends JpaRepository<Reglement, Long> {
    List<Reglement> findReglementByModeDePaiement(ModeDePaiement modeDePaiement);
    Long countReglementByModeDePaiement(ModeDePaiement modeDePaiement);
    Long countReglementByTranche(Tranche tranche);
}
