package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "module")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idModule", nullable = false)
    Long id;

    @Column(unique = true)
    String nom;

    @OneToMany(mappedBy = "module")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "competence"})
    String description;

    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("module")
    List<Matiere> matieres;
}
