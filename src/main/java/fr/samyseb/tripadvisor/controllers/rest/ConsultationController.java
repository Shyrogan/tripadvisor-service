package fr.samyseb.tripadvisor.controllers.rest;

import fr.samyseb.tripadvisor.pojos.Offre;
import fr.samyseb.tripadvisor.services.OffreService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final OffreService offreService;

    @GetMapping
    public List<Offre> getOffres(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                                 @RequestParam(required = false) Float prixMin,
                                 @RequestParam(required = false) Float prixMax) {
        return offreService.create(debut, fin, prixMin, prixMax);
    }

}
