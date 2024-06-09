package tn.esprit.spring.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
/*
    @Override
    public List<seanceClasseDto> getAllSeanceclasses2() {
        List<SeanceClasse> seanceClasses = seanceclasseRepository.findAll();
        return seanceClasses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private seanceClasseDto convertToDTO(SeanceClasse seanceClasse) {
        seanceClasseDto dto = new seanceClasseDto();
        dto.setIdSeance(seanceClasse.getIdSeance());
        dto.setHeureDebut(seanceClasse.getHeureDebut());
        dto.setHeureFin(seanceClasse.getHeureFin());

        seanceClasseDto.SalleDTO salleDTO = new seanceClasseDto.SalleDTO();
        salleDTO.setNom_salle(seanceClasse.getSalle().getNom_salle());
        dto.setSalle(salleDTO);

        seanceClasseDto.MatiereDTO matiereDTO = new seanceClasseDto.MatiereDTO();
        matiereDTO.setNomMatiere(seanceClasse.getMatiere().getNomMatiere());
        dto.setMatiere(matiereDTO);

        seanceClasseDto.ClasseDTO classeDTO = new seanceClasseDto.ClasseDTO();
        classeDTO.setNomClasse(seanceClasse.getClasse().getNomClasse());
        dto.setClasse(classeDTO);

        String enseignantFullName = seanceClasse.getEnseignant().getPrenom() + " " + seanceClasse.getEnseignant().getNom();
        dto.setEnseignant(enseignantFullName);

        return dto;
    }*/
}

