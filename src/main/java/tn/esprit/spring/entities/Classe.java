package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "classe")
@ToString(exclude = {"utilisateurs","matieres"})
//@JsonIgnoreProperties({"utilisateurs", "matieres"})

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClasse", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;


    @Column(name = "nomClasse", nullable = false)
    private String nomClasse;

 

    @ManyToMany
    @JoinTable(
            name = "classe_matiere", // Added JoinTable annotation
            joinColumns = @JoinColumn(name = "classe_id"),
            inverseJoinColumns = @JoinColumn(name = "matiere_id"))
    @JsonManagedReference(value = "classe-matieres")
    private List<Matiere> matieres;


    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "classe-users")
    private List<Utilisateur> utilisateurs;


    }
