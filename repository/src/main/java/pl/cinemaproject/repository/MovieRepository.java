package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.Movie;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Integer> {


    Optional<Movie> findByTitle(String title);
    List<Movie> findByDescription(String description);
    List<Movie> findByStartTime(Date startTime);
}
