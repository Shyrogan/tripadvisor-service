package fr.samyseb.tripadvisor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hotel")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nom;
    private int etoiles;
    @OneToOne
    private Adresse adresse;
    @OneToMany(mappedBy = "hotel")
    @Getter(onMethod = @__(@JsonIgnore))
    private List<Chambre> chambres;
    @OneToMany(mappedBy = "hotel")
    @Getter(onMethod = @__(@JsonIgnore))
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "hotel")
    @Getter(onMethod = @__(@JsonIgnore))
    private List<Partenariat> partenariats;
    private URL url;

}