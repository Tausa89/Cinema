package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.SeancesSeat;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.util.Optional;

public interface SeatSeanceRepository extends CrudRepository<SeancesSeat, Integer> {


    SeancesSeat findByCinemaRoomId(Integer cinemaRoomId);

    Optional<SeancesSeat> findBySeanceIdAndSeatId(Integer seanceId, Integer seatId);



}
