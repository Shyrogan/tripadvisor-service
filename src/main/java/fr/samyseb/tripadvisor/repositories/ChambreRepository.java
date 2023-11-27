package fr.samyseb.tripadvisor.repositories;

import fr.samyseb.tripadvisor.entities.Chambre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChambreRepository extends CrudRepository<Chambre, Long> {


    List<Chambre> findByHotelId(UUID hotelId);

    Optional<Chambre> findChambreByNumeroAndHotel_Id(long numero, UUID hotelId);


}
