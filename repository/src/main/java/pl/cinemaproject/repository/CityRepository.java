package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.util.Optional;

public interface CityRepository extends CrudRepository<City, Integer> {

    Optional<City> findByName(String name);
}
