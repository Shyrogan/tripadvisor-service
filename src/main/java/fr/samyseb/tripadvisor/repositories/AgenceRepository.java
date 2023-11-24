package fr.samyseb.tripadvisor.repositories;

import fr.samyseb.tripadvisor.entities.Agence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgenceRepository extends CrudRepository<Agence, UUID> {
}
