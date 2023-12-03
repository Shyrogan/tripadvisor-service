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
import java.util.stream.Stream;
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

        // Regrouper les offres par chambre d'hôtel
        Map<String, List<Offre>> groupedOffres = offres.stream()
                .collect(Collectors.groupingBy(offre -> offre.hotel().id() + "-" + offre.chambre().numero()));

        // Trouver les offres les moins chères pour chaque groupe
        List<Offre> offresMoinsCheres = groupedOffres.values().stream()
                .flatMap(listeOffres -> {
                    Offre offreMoinsChere = listeOffres.stream()
                            .min(Comparator.comparing(Offre::prixSejour))
                            .orElse(null);

                    if (offreMoinsChere == null) {
                        return Stream.empty();
                    }

                    float prixMinimum = offreMoinsChere.prixSejour();
                    return listeOffres.stream()
                            .filter(offre -> offre.prixSejour() == prixMinimum);
                })
                .collect(Collectors.toList());

        // Trier les offres finales par prix par nuit
        return offresMoinsCheres.stream()
                .sorted(Comparator.comparing(offre -> {
                    long nbNuits = ChronoUnit.DAYS.between(debut, fin);
                    return offre.prixSejour() / nbNuits;
                }))
                .collect(Collectors.toList());
    }
}