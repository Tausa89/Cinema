package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.Seat;
import pl.cinemaproject.repository.SeatRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.Optional;

public class SeatRepositoryImpl extends AbstractCrudRepository<Seat,Integer> implements SeatRepository {

    public SeatRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    @Override
    public Optional<Seat> findByRowNumberPlaceNumberAndCinemaRoomId(Integer rowNumber, Integer placeNumber, Integer cinemaRoomId) {

        final var SQL = "select * from seats where row_amount = :rowNumber and place = :placeNumber and cinema_room_id = :id";
        return jdbi.withHandle(handle -> handle
                .createQuery(SQL)
                .bind("rowNumber", rowNumber)
                .bind("placeNumber", placeNumber)
                .bind("id", cinemaRoomId)
                .mapToBean(Seat.class)
                .findFirst());
    }
}
