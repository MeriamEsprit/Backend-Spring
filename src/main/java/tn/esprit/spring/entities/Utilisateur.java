package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ToString(exclude = {"notes", "classe","competence"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;

    private String identifiant;

    private String cin;

    private String photo;

    private String nom;

    private String prenom;

    private String email;

    private String motDePasse;

    private boolean isHidden;

    private ERole role ;

    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-notes")
    List<Note> notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    @JsonBackReference(value = "classe-users")
    Classe classe;

/*
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "utilisateur")
    Set<Reglement> reglements;
*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCompetence")
    @JsonBackReference(value = "competence-users")
    @JsonIgnore
    Competence competence;

    public Utilisateur(String email, String motDePasse) {
        this.motDePasse = motDePasse;
        this.email = email;
    }

    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"utilisateur"})
    private List<Presence> presences;
}