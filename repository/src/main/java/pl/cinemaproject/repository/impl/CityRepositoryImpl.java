package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.repository.CityRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.Optional;

public class CityRepositoryImpl extends AbstractCrudRepository<City, Integer> implements CityRepository {

    public CityRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }


    @Override
    public Optional<City> findByName(String name) {

        var sql = "select * from cities where name = :name";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name", name)
                .mapToBean(City.class)
                .findFirst());
    }
}
