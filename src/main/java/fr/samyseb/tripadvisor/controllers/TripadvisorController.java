package fr.samyseb.tripadvisor.controllers;

import fr.samyseb.tripadvisor.entities.CarteBancaire;
import fr.samyseb.tripadvisor.entities.Client;
import fr.samyseb.tripadvisor.entities.Reservation;
import fr.samyseb.tripadvisor.pojos.InfoClient;
import fr.samyseb.tripadvisor.pojos.Offre;
import fr.samyseb.tripadvisor.repositories.AgenceRepository;
import fr.samyseb.tripadvisor.repositories.ChambreRepository;
import fr.samyseb.tripadvisor.repositories.HotelRepository;
import fr.samyseb.tripadvisor.services.OffreService;
import fr.samyseb.tripadvisor.services.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TripadvisorController {

    private final HotelRepository hotelRepository;
    private final AgenceRepository agenceRepository;
    private final ChambreRepository chambreRepository;
    private final OffreService offreService;
    private final ReservationService reservationService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("hotels", StreamSupport
                .stream(hotelRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));


        model.addAttribute("agences", StreamSupport
                .stream(agenceRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));

        return "index";
    }

    @GetMapping("/recherche")
    public String recherche(Model model) {
        return "recherche";
    }

    @GetMapping("/result")
    public String result(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @RequestParam(required = false) Float prixMin,
            @RequestParam(required = false) Float prixMax,
            Model model) {
        model.addAttribute("offres", offreService.create(debut, fin, prixMin, prixMax));
        return "result";
    }

    @GetMapping("/reservation")
    @Transactional
    public String reservation(@RequestParam UUID agenceId,
                              @RequestParam UUID hotelId,
                              @RequestParam Integer chambreId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                              @RequestParam Float prixSejour,
                              Model model) {
        var agence = agenceRepository.findById(agenceId).orElseThrow(IllegalArgumentException::new);
        var hotel = hotelRepository.findById(hotelId).orElseThrow(IllegalArgumentException::new);
        var chambre = chambreRepository.findChambreByNumeroAndHotel_Id(chambreId, hotelId).orElseThrow(IllegalArgumentException::new);

       InfoClient infoClient = new InfoClient();

        model.addAttribute("agenceId", agenceId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("chambreId", chambreId);
        model.addAttribute("debut", debut);
        model.addAttribute("fin", fin);
        model.addAttribute("prixSejour", prixSejour);
        model.addAttribute("infoClient", infoClient);


        return "reservation";
    }

    @PostMapping("/submitReservation")
    @Transactional
    public String submitReservation(@ModelAttribute InfoClient infoclient,
                                    @RequestParam UUID agenceId,
                                    @RequestParam UUID hotelId,
                                    @RequestParam Integer chambreId,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                                    @RequestParam Float prixSejour,
                                    Model model) {

        var agence = agenceRepository.findById(agenceId).orElseThrow(IllegalArgumentException::new);
        var hotel = hotelRepository.findById(hotelId).orElseThrow(IllegalArgumentException::new);
        var chambre = chambreRepository.findChambreByNumeroAndHotel_Id(chambreId, hotelId).orElseThrow(IllegalArgumentException::new);

        System.out.println("info reçu: " + infoclient.getNom() + infoclient.getPrenom() + infoclient.getNumero() +"crpyto: "+ infoclient.getCryptogramme());

        CarteBancaire cb = CarteBancaire.builder()
                        .numero(infoclient.getNumero())
                        .annee(infoclient.getAnnee())
                        .mois(infoclient.getMois())
                        .cryptogramme(infoclient.getCryptogramme())
                        .build();

        Client client = Client.builder()
                .nom(infoclient.getNom())
                .prenom(infoclient.getPrenom())
                .build();

        client.carteBancaire(cb);



        Offre offre = Offre.builder()
                .prixSejour(prixSejour)
                .agence(agence)
                .chambre(chambre)
                .hotel(hotel)
                .debut(debut)
                .fin(fin)
                .build();

        Reservation reservation = reservationService.reserver(offre, client);

        model.addAttribute("reservation", reservation);
        return "confirm";
    }

}
