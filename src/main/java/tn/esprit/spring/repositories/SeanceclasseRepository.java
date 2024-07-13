package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.spring.entities.SeanceClasse;
import tn.esprit.spring.services.PlanningServiceImp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


public interface SeanceclasseRepository extends JpaRepository<SeanceClasse, Long> {

    @Query("SELECT s FROM SeanceClasse s WHERE s.enseignant.id = :idEnseignant")
    List<SeanceClasse> findByEnseignantId(Long idEnseignant);

    @Query("SELECT s FROM SeanceClasse s WHERE s.classe.id = :idClasse")
    List<SeanceClasse> findByClasseId(Long idClasse);
    List<SeanceClasse> findByHeureDebut(Instant heureDebut);
    @Query("SELECT s FROM SeanceClasse s WHERE DATE(s.heureDebut) = :date")
    List<SeanceClasse> findByDate(@Param("date") LocalDate date);

    @Query("SELECT YEAR(s.heureDebut) AS year, MONTH(s.heureDebut) AS month, SUM(TIMESTAMPDIFF(s.heureDebut, s.heureFin)/3600) AS totalDuration " +
            "FROM SeanceClasse s " +
            "WHERE s.enseignant = :idEnseignant " +
            "GROUP BY YEAR(s.heureDebut), MONTH(s.heureDebut)")
    List<PlanningServiceImp.MonthlyHours> findMonthlyTeachingTime(@Param("idEnseignant") Long idEnseignant);

    @Query(value = "SELECT YEAR(heure_debut) AS year, MONTH(heure_debut) AS month, SUM(TIMEDIFF(heure_fin, heure_debut)) AS total_time " +
            "FROM seanceclasse " +
            "WHERE id_classe = :idClasse " +
            "GROUP BY YEAR(heure_debut), MONTH(heure_debut)", nativeQuery = true)
    List<PlanningServiceImp.MonthlyHours> findMonthlyTeachingTimeByClass(@Param("idClasse") Long idClasse);



}

