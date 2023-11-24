package fr.samyseb.hotelservice.controllers;

import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.pojos.Reservation;
import fr.samyseb.hotelservice.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/reservation")
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
