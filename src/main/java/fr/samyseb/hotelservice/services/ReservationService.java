package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.pojos.Client;
import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.pojos.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ReservationService {

    public Reservation reserver(Offre offre, Client fillableClient) {
        return WebClient.create()
                .post()
                .uri(format("%s/reservation", offre.agence().url()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(Reservation.builder()
                        .hotel(offre.hotel())
                        .chambre(offre.chambre())
                        .debut(offre.debut())
                        .fin(offre.fin())
                        .client(fillableClient)
                        .build()), Reservation.class)
                .retrieve()
                .bodyToMono(Reservation.class)
                .block();
    }

}
