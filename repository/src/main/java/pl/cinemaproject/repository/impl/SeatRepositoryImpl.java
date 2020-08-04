package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.Seat;
import pl.cinemaproject.repository.SeatRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

public class SeatRepositoryImpl extends AbstractCrudRepository<Seat,Integer> implements SeatRepository {

    public SeatRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }


}
