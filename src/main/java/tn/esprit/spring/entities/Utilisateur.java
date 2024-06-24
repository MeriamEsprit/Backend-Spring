package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;

    private String identifiant;

    private String cin;

    private String nom;

    private String prenom;

    private String email;

    private String motDePasse;

    private boolean isHidden;

    private ERole role ;

    @OneToMany(mappedBy = "utilisateur")
    List<Note> notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "utilisateurs"}) // Prevent recursion
    Classe classe;

/*
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "utilisateur")
    Set<Reglement> reglements;
*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCompetence")
    Competence competence;

    public Utilisateur(String email, String motDePasse) {
        this.motDePasse = motDePasse;
        this.email = email;
    }

    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"utilisateur"})
    private List<Presence> presences;
}
