package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.util.Optional;

public interface CinemaRepository extends CrudRepository<Cinema, Integer> {


    Optional<Cinema> findByName(String name);


}
