package fr.samyseb.tripadvisor.services;

import fr.samyseb.tripadvisor.pojos.Offre;
import fr.samyseb.tripadvisor.repositories.AgenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OffreService {

    private final AgenceRepository agenceRepository;

    public List<Offre> create(LocalDate debut, LocalDate fin, Float prixMin, Float prixMax, Integer etoilesMin) {
        // Récupérer les offres
        List<Offre> offres = StreamSupport.stream(agenceRepository.findAll().spliterator(), true)
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
                .collect(Collectors.toList());

        var offresMap = offres.stream()
                .collect(Collectors.groupingBy(Offre::chambre))
                .values()
                .stream()
                .map(offreList -> offreList.stream().min(Comparator.comparingDouble(Offre::prixSejour)).get())
                .sorted(Comparator.comparingDouble(Offre::prixSejour))
                .toList();

        // Trier les offres finales par prix par nuit
        return offresMap;
    }
}