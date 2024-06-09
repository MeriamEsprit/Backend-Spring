package tn.esprit.spring.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Dto.emploiDuTemps.ClasseSummaryDto;
import tn.esprit.spring.Dto.emploiDuTemps.MatiereSummaryDto;
import tn.esprit.spring.Dto.emploiDuTemps.SalleSummaryDto;
import tn.esprit.spring.Dto.emploiDuTemps.SeanceClasseSummaryDto;
import tn.esprit.spring.entities.SeanceClasse;
import tn.esprit.spring.repositories.SeanceclasseRepository;
//import tn.esprit.spring.Dto.emploiDuTemps.seanceClasseDto;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SeanceclasseServicesImpl implements ISeanceclasseServices {

    @Autowired
    private SeanceclasseRepository seanceclasseRepository;

    @Override
    public SeanceClasse saveSeanceclasse(SeanceClasse seanceClasse) {
        return seanceclasseRepository.save(seanceClasse);
    }

    @Override
    public SeanceClasse updateSeanceclasse(SeanceClasse seanceclasse) {
        return seanceclasseRepository.save(seanceclasse);
    }

    @Override
    public void deleteSeanceclasse(Long id) {
        seanceclasseRepository.deleteById(id);
    }

    @Override
    public SeanceClasse getSeanceclasseById(Long id) {
        return seanceclasseRepository.findById(id).orElse(null);
    }

    @Override
    public List<SeanceClasse> getAllSeanceclasses() {
        return seanceclasseRepository.findAll();
    }

    @Override
    public List<SeanceClasseSummaryDto> getAllSeanceClasseSummaries() {
        List<SeanceClasse> seanceClasses = seanceclasseRepository.findAll();

        return seanceClasses.stream()
                .map(seanceClasse -> {
                    SeanceClasseSummaryDto dto = new SeanceClasseSummaryDto();
                    dto.setIdSeance(seanceClasse.getIdSeance());
                    dto.setHeureDebut(seanceClasse.getHeureDebut().toString());
                    dto.setHeureFin(seanceClasse.getHeureFin().toString());

                    if (seanceClasse.getSalle() != null) {
                        SalleSummaryDto salleDto = new SalleSummaryDto(seanceClasse.getSalle().getNom_salle());
                        dto.setSalle(salleDto);
                    }

                    if (seanceClasse.getMatiere() != null) {
                        MatiereSummaryDto matiereDto = new MatiereSummaryDto(seanceClasse.getMatiere().getNomMatiere());
                        dto.setMatiere(matiereDto);
                    }

                    if (seanceClasse.getClasse() != null) {
                        ClasseSummaryDto classeDto = new ClasseSummaryDto(seanceClasse.getClasse().getNomClasse());
                        dto.setClasse(classeDto);
                    }

                    if (seanceClasse.getEnseignant() != null) {
                        String nomPrenom = seanceClasse.getEnseignant().getNom() + " " + seanceClasse.getEnseignant().getPrenom();
                        dto.setEnseignantNomPrenom(nomPrenom);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }
}

