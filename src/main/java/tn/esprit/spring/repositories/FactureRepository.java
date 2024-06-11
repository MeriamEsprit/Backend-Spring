package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tn.esprit.spring.entities.Facture;

public interface FactureRepository extends JpaRepository<Facture, Long>, JpaSpecificationExecutor<Facture> {


}
