package fr.samyseb.tripadvisor.services;

import fr.samyseb.tripadvisor.pojos.Offre;
import fr.samyseb.tripadvisor.repositories.AgenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OffreService {

    private final AgenceRepository agenceRepository;

    public List<Offre> create(LocalDate debut, LocalDate fin, Float prixMin, Float prixMax, Integer etoilesMin) {
        return StreamSupport.stream(agenceRepository.findAll().spliterator(), true)
                .map(agence -> WebClient.create()
                        .get()
                        .uri(b -> b.scheme(agence.url().getProtocol())
                                .host(agence.url().getHost())
                                .path("/consultation")
                                .port(agence.url().getPort())
                                .queryParam("debut", debut)
                                .queryParam("fin", fin)
                                .queryParam("prixMin", prixMin)
                                .queryParam("prixMax", prixMax)
                                .queryParam("etoilesMin", etoilesMin)
                                .build())
                        .retrieve()
                        .bodyToMono(Offre[].class)
                        .block())
                .filter(Objects::nonNull)
                .flatMap(Arrays::stream)
                .map(offre -> {
                    long nbNuits = ChronoUnit.DAYS.between(debut, fin);
                    float prixParNuit = offre.prixSejour() / nbNuits;
                    return new AbstractMap.SimpleEntry<>(offre, prixParNuit);
                })
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
