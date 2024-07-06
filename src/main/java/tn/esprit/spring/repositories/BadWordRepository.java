package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.BadWord;

public interface BadWordRepository extends JpaRepository<BadWord, Long> {
}
