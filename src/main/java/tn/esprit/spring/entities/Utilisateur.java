package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "utilisateur")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idUtilisateur", nullable = false)
    Long id;

    private String nom;

    private String prenom;

    private String email;

    private String motDePasse;

    private boolean isHidden;

    private ERole role ;

    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("utilisateur")
    List<Note> notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "utilisateurs"})
    Classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCompetence")
    Competence competence;

    public Utilisateur(String email, String motDePasse) {
        this.motDePasse = motDePasse;
        this.email = email;
    }
}
