package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Module;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {

}
