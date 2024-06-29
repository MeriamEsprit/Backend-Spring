package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Dto.DtoReclamation;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.repositories.ReclamationRepository;
import tn.esprit.spring.repositories.UtilisateurRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReclamationServices implements IReclamationServices {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public DtoReclamation createReclamation(DtoReclamation dtoReclamation) {
        Reclamation reclamation = mapToEntity(dtoReclamation);
        reclamation = reclamationRepository.save(reclamation);
        return mapToDto(reclamation);
    }

    @Override
    public DtoReclamation updateReclamation(DtoReclamation dtoReclamation) {
        Reclamation reclamation = mapToEntity(dtoReclamation);
        reclamation = reclamationRepository.save(reclamation);
        return mapToDto(reclamation);
    }
    @Override
    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }

    @Override
    public DtoReclamation getReclamationById(Long id) {
        Reclamation reclamation = reclamationRepository.findById(id).orElse(null);
        return reclamation != null ? mapToDto(reclamation) : null;
    }

    @Override
    public List<DtoReclamation> getAllReclamations() {
        return reclamationRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoReclamation> getAllReclamationsByUser(Long userId) {
        List<Reclamation> reclamations = reclamationRepository.findAllByUtilisateurId(userId);
        return reclamations.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private Reclamation mapToEntity(DtoReclamation dtoReclamation) {
        Reclamation reclamation = new Reclamation();
        reclamation.setId(dtoReclamation.getId());
        reclamation.setSubject(dtoReclamation.getSubject());
        reclamation.setDescription(dtoReclamation.getDescription());
        reclamation.setStatut(dtoReclamation.getStatut());
        reclamation.setDate(dtoReclamation.getDate());

        Utilisateur utilisateur = utilisateurRepository.findById(dtoReclamation.getUtilisateurId()).orElse(null);
        reclamation.setUtilisateur(utilisateur);
        return reclamation;
    }

    private DtoReclamation mapToDto(Reclamation reclamation) {
        DtoReclamation dtoReclamation = new DtoReclamation();
        dtoReclamation.setId(reclamation.getId());
        dtoReclamation.setSubject(reclamation.getSubject());
        dtoReclamation.setDescription(reclamation.getDescription());
        dtoReclamation.setStatut(reclamation.getStatut());
        dtoReclamation.setDate(reclamation.getDate());

        if (reclamation.getUtilisateur() != null) {
            dtoReclamation.setUtilisateurId(reclamation.getUtilisateur().getId());
        }
        return dtoReclamation;
    }
}