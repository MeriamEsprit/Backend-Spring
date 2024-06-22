package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "reglement")
public class Reglement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReglement", nullable = false)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "ModeDePaiement")
    private ModeDePaiement modeDePaiement;


    @Enumerated(EnumType.STRING)
    @Column(name = "Tranche")
    private Tranche tranche;
/*
    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER)
    Utilisateur utilisateur;
*/
    @OneToMany(mappedBy = "reglement",cascade = CascadeType.ALL)
    Set<Facture> facteurs;


    public Reglement() {

    }
}