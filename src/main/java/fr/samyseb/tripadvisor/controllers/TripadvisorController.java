package fr.samyseb.hotelservice.controllers;

import fr.samyseb.hotelservice.entities.Agence;
import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.repositories.AgenceRepository;
import fr.samyseb.hotelservice.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController("/api")
@RequiredArgsConstructor
public class TripadvisorController {

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
