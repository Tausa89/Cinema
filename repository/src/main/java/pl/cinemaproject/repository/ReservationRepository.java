package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.Reservation;
import pl.cinemaproject.repository.generic.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation,Integer> {


}
