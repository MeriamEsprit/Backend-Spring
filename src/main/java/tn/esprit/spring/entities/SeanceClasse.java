package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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

    @Column(name = "heureDebut", nullable = false)
    private LocalDateTime heureDebut;

    @Column(name = "heureFin", nullable = false)
    private LocalDateTime heureFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSalle")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "seanceClasses"})
    private Salle salle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMatiere")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "seanceClasses"})
    private Matiere matiere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idClasse")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "seanceClasses"})
    private Classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEnseignant")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "seanceClasses"})
    private Utilisateur enseignant;
}
