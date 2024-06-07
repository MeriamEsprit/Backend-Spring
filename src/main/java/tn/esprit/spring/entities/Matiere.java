package tn.esprit.spring.entities;

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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "matieres"})
    Module module;

    @OneToMany(mappedBy = "matiere", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("matiere")
    List<Note> notes;


}
