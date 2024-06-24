package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "disponibilite")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idDisponibilite", nullable = false)
    private Long idDisponibilite;

    @Column(name = "semaine")
    private Integer semaine;

    @Column(name = "jourSemaine")
    private Integer jourSemaine;

    @Column(name = "heureDebut")
    private Instant heureDebut;

    @Column(name = "heureFin")
    private Instant heureFin;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur enseignant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSalle")
    private Salle salle;

}
