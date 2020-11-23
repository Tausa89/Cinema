package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.SeancesSeat;
import pl.cinemaproject.repository.SeatSeanceRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.Optional;

public class SeatSeanceRepositoryImpl extends AbstractCrudRepository<SeancesSeat,Integer> implements SeatSeanceRepository {

    public SeatSeanceRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }


    @Override
    public SeancesSeat findByCinemaRoomId(Integer cinemaRoomId) {

        final var SQL = """
                Select *
                from seances_seats ss
                join seats s on ss.seat_id = s.id
                join cinema_rooms cr on s.cinema_room_id = cr.id
                where cr.id = :id;
                """;

        return jdbi.withHandle(handle -> handle
                .createQuery(SQL)
                .bind("id", cinemaRoomId)
                .mapToBean(SeancesSeat.class)
                .findFirst()).orElseThrow();

    }


    @Override
    public Optional<SeancesSeat> findBySeanceIdAndSeatId(Integer seanceId, Integer seatId) {

        final var SQL = """
                Select *
                from seances_seats
                where seance_id = :seanceId
                and seat_id = :seatId;
                """;

        return jdbi.withHandle(handle -> handle
                .createQuery(SQL)
                .bind("seanceId",seanceId)
                .bind("seatId", seatId)
                .mapToBean(SeancesSeat.class)
                .findFirst());
    }
}
