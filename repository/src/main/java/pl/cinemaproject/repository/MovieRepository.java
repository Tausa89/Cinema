package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.Movie;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Integer> {


    Optional<Movie> findByName(String movieName);
    Optional<Movie> findByDescription(String description);
    Optional<Movie> findByStartTime(LocalDate startTime);
}
