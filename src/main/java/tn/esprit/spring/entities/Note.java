package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "note")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString(exclude = {"utilisateur"})
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idNote", nullable = false)
    Long id;

    Double noteCc;

    Double noteTp;

    Double noteExamen;

    Double notePrincipale;

    Double noteControle;

    Double MoyennePrincipale;

    Double MoyenneControle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference(value = "user-notes")
    Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matiere_id")
/*
    @JsonBackReference("matiere-notes")
*/
    Matiere matiere;
}
