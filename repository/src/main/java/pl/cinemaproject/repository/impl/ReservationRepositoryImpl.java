package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.Reservation;
import pl.cinemaproject.repository.ReservationRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

public class ReservationRepositoryImpl extends AbstractCrudRepository<Reservation,Integer> implements ReservationRepository {

    public ReservationRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }
}
