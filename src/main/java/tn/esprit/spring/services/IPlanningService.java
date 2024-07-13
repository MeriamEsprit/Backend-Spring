package tn.esprit.spring.services;

import tn.esprit.spring.Dto.emploiDuTemps.SeanceClasseDTO;
import tn.esprit.spring.entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface IPlanningService {


    void generateSchedule(LocalDate startDate);

    SeanceClasseDTO createSeanceDTO(Classe classe, Matiere matiere, Utilisateur enseignant, Long salleId, LocalDate startDate);

    LocalDateTime findNextAvailableTime(LocalDate startDate, Classe classe, Utilisateur enseignant);

    Long findAvailableSalle(LocalDate date);

    //List<PlanningServiceImp.MonthlyHours> getMonthlyTeachingTime(int idEnseignant);

    List<PlanningServiceImp.MonthlyHours> getMonthlyTeachingTime();
}
