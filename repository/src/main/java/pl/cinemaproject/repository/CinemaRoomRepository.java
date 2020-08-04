package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.util.Optional;

public interface CinemaRoomRepository extends CrudRepository<CinemaRoom, Integer> {

    Optional<CinemaRoom> findByName(String name);
}
