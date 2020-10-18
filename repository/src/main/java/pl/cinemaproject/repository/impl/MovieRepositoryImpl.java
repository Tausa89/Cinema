package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.Movie;
import pl.cinemaproject.repository.MovieRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Movie> findByDescription(String description) {

        var sql = "select * from movies where description like :description";

        return jdbi.withHandle(handle ->
                handle
                        .createQuery(sql)
                        .bind("description", description)
                        .mapToBean(Movie.class)
                        .stream()
                        .collect(Collectors.toList()));
    }

    @Override
    public Optional<Movie> findByStartTime(LocalDate startTime) {
        return Optional.empty();
    }
}
