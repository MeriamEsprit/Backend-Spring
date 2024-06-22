package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "classe")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idClasse", nullable = false)
    private Long id;

    @Column(name = "nomClasse")
    private String nomClasse;

    @OneToMany(mappedBy = "classe")
    List<Utilisateur> utilisateurs;
}
