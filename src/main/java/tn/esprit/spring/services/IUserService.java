package tn.esprit.spring.services;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.entities.Utilisateur;

import java.util.List;

public interface IUserService {
    List<Utilisateur> getAllUserByRole(ERole role);

}
