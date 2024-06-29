package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Justification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJustification;
    private String name;
    private String reason;
    private int status;
    private Date submissionDate;
    private Date validationDate;
    private String filePath;

    @OneToMany(mappedBy = "justification", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Presence> presences = new ArrayList<>();
}
