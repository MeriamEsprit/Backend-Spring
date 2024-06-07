/*package tn.esprit.spring.entities;

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
@Table(name = "seanceclasse")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Seanceclasse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idSeance", nullable = false)
    private Long idSeance;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "semaine")
    private Integer semaine;

    @Column(name = "jour")
    private Integer jour;

    @Column(name = "heureDebut")
    private Instant heureDebut;

    @Column(name = "heureFin")
    private Instant heureFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id")
    private Salle salle;

}
*/
package tn.esprit.spring.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "seanceclasse")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SeanceClasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idSeance", nullable = false)
    private Long idSeance;

    @Column(name = "annee", nullable = false)
    private Integer annee;

    @Column(name = "semaine", nullable = false)
    private Integer semaine;

    @Column(name = "jour", nullable = false)
    private Integer jour;

    @Column(name = "heureDebut", nullable = false)
    private Instant heureDebut;

    @Column(name = "heureFin", nullable = false)
    private Instant heureFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_id", nullable = false)
    private Matiere matiere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Utilisateur enseignant;
}

