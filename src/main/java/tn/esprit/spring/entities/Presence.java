package tn.esprit.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "presence")
public class Presence {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "etat")
    private Boolean etat;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "justification_id")
    private Justification justification;

}