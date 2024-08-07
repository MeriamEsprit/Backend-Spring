package tn.esprit.spring.repositories;

import org.apache.xpath.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Module;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    Optional<Module> findByNom(String nom);
    Boolean existsByNom(String nom);
}
