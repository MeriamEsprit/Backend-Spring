package tn.esprit.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString(exclude = {"utilisateur", "justification","classe"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "presence")
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long idPresence;
    private Boolean etatPresence;
    private LocalDate datePresence;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id")
    @JsonIgnoreProperties({"notes","presences"})
    Utilisateur utilisateur;

  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "justification_id")
    @JsonBackReference
    private Justification justification;

}