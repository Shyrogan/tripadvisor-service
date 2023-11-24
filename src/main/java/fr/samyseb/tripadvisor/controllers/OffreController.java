package fr.samyseb.tripadvisor.controllers;

import fr.samyseb.tripadvisor.pojos.Offre;
import fr.samyseb.tripadvisor.services.OffreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/offre")
@RequiredArgsConstructor
public class OffreController {

    private final OffreService offreService;

    @GetMapping("/")
    public void listOffres(@RequestBody Offre offre) {
        offreService.create(offre.debut(), offre.fin());
    }

}
