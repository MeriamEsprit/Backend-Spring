package tn.esprit.spring.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "matiere")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
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
    Module module;


}
