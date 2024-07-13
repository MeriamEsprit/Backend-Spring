package tn.esprit.spring.repositories;
import tn.esprit.spring.entities.JourFerie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JourFerieRepository extends JpaRepository<JourFerie, Long> {
    List<JourFerie> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
