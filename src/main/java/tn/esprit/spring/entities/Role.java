package tn.esprit.spring.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nom_role", nullable = false, unique = true)
    private ERole nomRole;

    // Constructors, getters, and setters

    public Role() {}

    public Role(ERole nomRole) {
        this.nomRole = nomRole;
    }
}
