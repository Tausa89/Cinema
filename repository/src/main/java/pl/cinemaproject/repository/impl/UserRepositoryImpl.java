package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.User;
import pl.cinemaproject.repository.UserRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.Optional;

public class UserRepositoryImpl extends AbstractCrudRepository<User,Integer> implements UserRepository {


    public UserRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    @Override
    public Optional<User> findByUsername(String username) {

        var sql = "select * from users where username = :username";


        return jdbi.withHandle(handle ->
                handle
                        .createQuery(sql)
                        .bind("username", username)
                        .mapToBean(User.class)
                        .findFirst());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var sql = "select * from users where email = :email";


        return jdbi.withHandle(handle ->
                handle
                        .createQuery(sql)
                        .bind("email", email)
                        .mapToBean(User.class)
                        .findFirst());
    }
}
