package fr.samyseb.tripadvisor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client")
@Setter
@AllArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nom;
    private String prenom;
    @OneToMany(cascade = CascadeType.ALL)
    @Getter(onMethod = @__(@JsonIgnore))
    private List<Reservation> reservations;
    @OneToOne(cascade = CascadeType.ALL)
    private CarteBancaire carteBancaire;

    public Client() {
        carteBancaire = new CarteBancaire(this);

    }
}


