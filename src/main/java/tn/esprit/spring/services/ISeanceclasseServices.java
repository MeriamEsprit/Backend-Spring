package tn.esprit.spring.services;

import tn.esprit.spring.Dto.emploiDuTemps.SeanceClasseDTO;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.SeanceClasse;

import java.time.LocalDateTime;
import java.util.List;

public interface ISeanceclasseServices {
    SeanceClasse saveSeanceclasse(SeanceClasse seanceclasse);


    SeanceClasse addSeanceClasse(SeanceClasseDTO dto);

    SeanceClasse updateSeanceClasse(Long idSeance, SeanceClasseDTO dto);

    void deleteSeanceclasse(Long id);
    SeanceClasse getSeanceclasseById(Long id);
    List<SeanceClasse> getAllSeanceclasses();

   // List<SeanceClasseSummaryDto> getAllSeanceClasseSummaries();

    //List<seanceClasseDto> getAllSeanceclasses2();
}
