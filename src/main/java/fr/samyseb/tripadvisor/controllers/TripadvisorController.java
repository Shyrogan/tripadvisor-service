package fr.samyseb.tripadvisor.controllers;

import fr.samyseb.tripadvisor.entities.Client;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        var client = new Client();

        Runnable r = () -> {
            System.out.println(client);
            /** reservationService.reserver(Offre.builder()
             .prixSejour(prixSejour)
             .agence(agence)
             .chambre(chambre)
             .hotel(hotel)
             .debut(debut)
             .fin(fin).build(), client)**/
        };

        model.addAttribute("client", client);
        model.addAttribute("reserver", r);

        return "reservation";
    }

}
