package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.status;

public interface FactureRepository extends JpaRepository<Facture, Long>, JpaSpecificationExecutor<Facture> {

    Long countFactureByStatus(status status);
}
