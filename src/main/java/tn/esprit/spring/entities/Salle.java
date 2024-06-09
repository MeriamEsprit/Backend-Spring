package tn.esprit.spring.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "salle")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idSalle", nullable = false)
    private Long idSalle;

    @Column(name = "nom")
    private String nom_salle;

    @Column(name = "capacite")
    private Integer capacite;

}
