package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "competence")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idCompetence", nullable = false)
    private Long idCompetence;

    @Column(name = "nomCompetence")
    private String nomCompetence;

    @OneToMany(mappedBy = "competence", fetch = FetchType.LAZY)
    List<Utilisateur> utilisateurs;

    @OneToMany(mappedBy = "competence", fetch = FetchType.LAZY)
    List<Matiere> matieres;

}
