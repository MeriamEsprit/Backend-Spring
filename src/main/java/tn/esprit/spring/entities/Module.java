package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@ToString(exclude = {"matieres"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idModule", nullable = false)
    Long id;

    @Column(unique = true)
    String nom;
    String description;

    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
    @JsonBackReference
    List<Matiere> matieres;
}
