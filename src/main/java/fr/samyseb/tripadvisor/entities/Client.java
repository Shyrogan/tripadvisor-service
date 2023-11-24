package fr.samyseb.tripadvisor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nom;
    private String prenom;
    @OneToMany
    @Getter(onMethod = @__(@JsonIgnore))
    private List<Reservation> reservations;
    @OneToOne
    private CarteBancaire carteBancaire;

}
