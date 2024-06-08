package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "classe")
public class Classe  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idClasse", nullable = false)
    private Long id;

    @Column(name = "nomClasse")
    private String nomClasse;

    @OneToMany(mappedBy = "classe")
    @JsonIgnoreProperties("classe")
    private List<Utilisateur> utilisateurs;

    @OneToMany(mappedBy = "classe")
    @JsonIgnoreProperties("classe")
    private List<Presence> presences;
}
