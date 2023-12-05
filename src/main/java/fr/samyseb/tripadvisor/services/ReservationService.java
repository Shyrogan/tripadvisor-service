package fr.samyseb.tripadvisor.services;

import fr.samyseb.tripadvisor.entities.Client;
import fr.samyseb.tripadvisor.entities.Reservation;
import fr.samyseb.tripadvisor.pojos.Offre;
import fr.samyseb.tripadvisor.pojos.ReservationRequest;
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
        var request = ReservationRequest.builder()
                .offre(offre)
                .client(fillableClient)
                .build();

        return WebClient.create()
                .post()
                .uri(format("%s/reservation", offre.agence().url()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), ReservationRequest.class)
                .retrieve()
                .bodyToMono(Reservation.class)
                .block();
    }
}