package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "matiere")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString(exclude = {"notes", "module", "classes"})

public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idMatiere", nullable = false)
    Long id;

    @Column(name = "nomMatiere")
    String nomMatiere;

    @Column(name = "nbreHeures")
    Integer nbreHeures;

    @Column(name = "coefficientTP")
    Double coefficientTP;

    @Column(name = "coefficientCC")
    Double coefficientCC;

    @Column(name = "coefficientExamen")
    Double coefficientExamen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    @JsonBackReference("module-matieres")
    Module module;

    @ManyToMany(mappedBy = "matieres")
    @JsonIgnore
    private List<Classe> classes;

    @OneToMany(mappedBy = "matiere")
    @JsonIgnore
    List<Note> notes;

    public void setClasse(Classe classe) {
        this.classes = List.of(classe); // Mettre à jour la liste des classes
    }
    @ManyToOne
    @JoinColumn(name = "competence_id")
    @JsonBackReference(value = "competence-matieres")
    private Competence competence;

}
