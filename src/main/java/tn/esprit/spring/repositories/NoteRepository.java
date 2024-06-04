package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.spring.entities.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findByUtilisateurId(Long utilisateurId);
    

}
