package fr.samyseb.tripadvisor.controllers;

import fr.samyseb.tripadvisor.repositories.AgenceRepository;
import fr.samyseb.tripadvisor.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller("/")
@RequiredArgsConstructor
public class TripadvisorController {

    private final HotelRepository hotelRepository;
    private final AgenceRepository agenceRepository;

    @GetMapping("/")
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

}
