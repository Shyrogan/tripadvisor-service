package fr.samyseb.tripadvisor.controllers;

import fr.samyseb.tripadvisor.pojos.Offre;
import fr.samyseb.tripadvisor.pojos.Reservation;
import fr.samyseb.tripadvisor.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/")
    public void reserver(@RequestBody Reservation reservation) {
        reservationService.reserver(Offre.builder()
                .agence(reservation.agence())
                .hotel(reservation.hotel())
                .chambre(reservation.chambre())
                .debut(reservation.debut())
                .fin(reservation.fin())
                .build(), reservation.client());
    }

}
