package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.User;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository <User, Integer> {


    Optional<User> findByUsername(String userName);
    Optional<User> findByEmail(String email);
}
