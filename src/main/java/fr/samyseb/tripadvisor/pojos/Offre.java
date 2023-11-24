package fr.samyseb.tripadvisor.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.samyseb.tripadvisor.entities.Agence;
import fr.samyseb.tripadvisor.entities.Hotel;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter(onMethod = @__(@JsonProperty))
public class Offre {

    private Hotel hotel;
    private Agence agence;
    private Chambre chambre;
    private LocalDate debut;
    private LocalDate fin;

}
