package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.Movie;
import pl.cinemaproject.repository.MovieRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.time.LocalDate;
import java.util.Optional;

public class MovieRepositoryImpl extends AbstractCrudRepository<Movie,Integer> implements MovieRepository {

    public MovieRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    @Override
    public Optional<Movie> findByTitle(String title) {

        var sql = "select * from movies where title = :title";
        return jdbi.withHandle(handle ->
                handle
                        .createQuery(sql)
                        .bind("title", title)
                        .mapToBean(Movie.class)
                        .findFirst());
    }

    @Override
    public Optional<Movie> findByDescription(String description) {
        return Optional.empty();
    }

    @Override
    public Optional<Movie> findByStartTime(LocalDate startTime) {
        return Optional.empty();
    }
}
