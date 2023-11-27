package fr.samyseb.tripadvisor.controllers.rest;

import fr.samyseb.tripadvisor.entities.Agence;
import fr.samyseb.tripadvisor.entities.Hotel;
import fr.samyseb.tripadvisor.repositories.AgenceRepository;
import fr.samyseb.tripadvisor.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RootController {

    private final AgenceRepository agenceRepository;
    private final HotelRepository hotelRepository;

    @GetMapping("/hotels")
    public List<Hotel> getAllHotels() {
        return StreamSupport.stream(hotelRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/agences")
    public List<Agence> getAllAgences() {
        return StreamSupport.stream(agenceRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
