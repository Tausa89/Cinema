package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.Seat;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.util.Optional;

public interface SeatRepository extends CrudRepository<Seat, Integer> {



    Optional<Seat> findByRowNumberPlaceNumberAndCinemaRoomId(Integer rowNumber, Integer placeNumber, Integer cinemaRoomId);
}
