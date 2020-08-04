package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.repository.CinemaRoomRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.Optional;

public class CinemaRoomRepositoryImpl extends AbstractCrudRepository<CinemaRoom, Integer> implements CinemaRoomRepository {

    public CinemaRoomRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    @Override
    public Optional<CinemaRoom> findByName(String name) {

        var sql = "select * from cinema_rooms where name = :name";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name", name)
                .mapToBean(CinemaRoom.class)
                .findFirst());
    }
}
