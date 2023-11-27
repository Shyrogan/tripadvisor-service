package fr.samyseb.tripadvisor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chambre")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
public class Chambre {


    @Id
    private long numero;
    private float prix;
    private int places;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @Getter(onMethod = @__(@JsonIgnore))
    private Hotel hotel;
    @Lob
    @Getter(onMethod = @__(@JsonIgnore))
    private byte[] image;
    @OneToMany(cascade = CascadeType.REMOVE)
    @Getter(onMethod = @__(@JsonIgnore))
    private List<Reservation> reservations;

}
