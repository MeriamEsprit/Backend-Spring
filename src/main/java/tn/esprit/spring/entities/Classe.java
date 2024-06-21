package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@ToString(exclude = {"utilisateurs"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "classe")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Classe  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "nomClasse")
    private String nomClasse;

    @OneToMany(mappedBy = "classe", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("")
    private List<Utilisateur> utilisateurs;

    }
