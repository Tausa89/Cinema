package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.repository.CinemaRoomRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @Override
    public List<CinemaRoom> findAllCinemaRoomsByCinemaId(Integer id) {

        var sql = "select * from cinema_rooms where cinema_id = :id";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("id", id))
                .mapToBean(CinemaRoom.class)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CinemaRoom> findCinemaRoomByNameWithCinemaId(String cinemaRoomName, Integer cinemaId) {

        var sql = "select * from cinema_rooms where cinema_id = :id and name = :cinemaRoomName";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("id", cinemaId)
                .bind("name", cinemaRoomName)
                .mapToBean(CinemaRoom.class)
                .findFirst());

    }
}
