package tn.esprit.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "disponibilite")
public class Disponibilite {
    @Id
    @Column(name = "idDisponibilite", nullable = false)
    private Long id;

    @Column(name = "semaine")
    private Integer semaine;

    @Column(name = "jourSemaine")
    private Integer jourSemaine;

    @Column(name = "heureDebut")
    private Instant heureDebut;

    @Column(name = "heureFin")
    private Instant heureFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id")
    private Utilisateur enseignant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id")
    private Salle salle;

}