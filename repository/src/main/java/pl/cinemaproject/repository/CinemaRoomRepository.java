package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CinemaRoomRepository extends CrudRepository<CinemaRoom, Integer> {

    Optional<CinemaRoom> findByName(String name);
    List<CinemaRoom> findAllCinemaRoomsByCinemaId(Integer id);
    Optional<CinemaRoom> findCinemaRoomByNameWithCinemaId(String cinemaRoomName, Integer cinemaId);

}
