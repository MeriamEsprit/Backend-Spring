package tn.esprit.spring.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "classe")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idClasse", nullable = false)
    private Long idClasse;

    @Column(name = "nomClasse")
    private String nomClasse;

    @OneToMany(mappedBy = "classe")
    List<Utilisateur> utilisateurs;
}
