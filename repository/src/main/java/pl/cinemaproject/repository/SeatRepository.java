package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.Seat;
import pl.cinemaproject.repository.generic.CrudRepository;

public interface SeatRepository extends CrudRepository<Seat, Integer> {
}
