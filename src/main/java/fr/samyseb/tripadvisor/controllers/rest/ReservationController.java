package fr.samyseb.tripadvisor.controllers.rest;

import fr.samyseb.tripadvisor.entities.Reservation;
import fr.samyseb.tripadvisor.pojos.ReservationRequest;
import fr.samyseb.tripadvisor.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public Reservation reserver(@RequestBody ReservationRequest reservation) {
        return reservationService.reserver(reservation.offre(), reservation.client());
    }

}
