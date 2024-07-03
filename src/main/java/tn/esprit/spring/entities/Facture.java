package tn.esprit.spring.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "facture")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFacture", nullable = false)
    private Long id;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "file")
    private byte[] file;
    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private status status = tn.esprit.spring.entities.status.EN_COURS_DE_TRAITEMENT;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    Reglement reglement;

    public void validerFacture() {
        this.status = status.VALIDE;
    }

    public void refuserFacture() {
        this.status = status.NON_VALIDE;
    }

}