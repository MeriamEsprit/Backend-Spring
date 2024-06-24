package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "classe")
@ToString(exclude = {"utilisateurs","matieres"})
//@JsonIgnoreProperties({"utilisateurs", "matieres"})

public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClasse", nullable = false)
    private Long id;

    @Column(name = "nomClasse")
    private String nomClasse;

    @OneToMany(mappedBy = "classe")
    @JsonManagedReference(value = "classe-users")
    List<Utilisateur> utilisateurs;

    @ManyToMany
    @JoinTable(
            name = "classe_matiere", // Added JoinTable annotation
            joinColumns = @JoinColumn(name = "classe_id"),
            inverseJoinColumns = @JoinColumn(name = "matiere_id"))
    @JsonManagedReference(value = "classe-matieres")
    private List<Matiere> matieres;
}
