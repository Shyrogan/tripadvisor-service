package fr.samyseb.tripadvisor.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.samyseb.tripadvisor.entities.Client;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
public class ReservationRequest {
    private Offre offre;
    private Client client;
}