package tn.esprit.spring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "classe")
public class Classe {
    @Id
    @Column(name = "idClasse", nullable = false)
    private Long id;

    @Column(name = "nomClasse")
    private String nomClasse;

    @OneToMany(mappedBy = "classe")
    List<Utilisateur> utilisateurs;
}
