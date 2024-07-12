package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.spring.entities.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note,Long> {


    @Query("SELECT n FROM Note n WHERE n.utilisateur.id = :userId")
    List<Note> findByUtilisateurId(@Param("userId") Long userId);
    Optional<Note> findByUtilisateurIdAndMatiereId(Long utilisateurId, Long matiereId);
    List<Note> findByMatiereId(Long matiereId);

}
