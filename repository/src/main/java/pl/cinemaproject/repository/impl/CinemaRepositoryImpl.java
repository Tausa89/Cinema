package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.repository.CinemaRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.Optional;

public class CinemaRepositoryImpl extends AbstractCrudRepository<Cinema,Integer> implements CinemaRepository {


    public CinemaRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }


    @Override
    public Optional<Cinema> findByName(String name) {

        var sql = "select * from cinemas where name = :name";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name", name)
                .mapToBean(Cinema.class)
                .findFirst());


    }


}
