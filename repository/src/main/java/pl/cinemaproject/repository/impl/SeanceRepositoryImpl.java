package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.Seance;
import pl.cinemaproject.repository.SeanceRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

public class SeanceRepositoryImpl extends AbstractCrudRepository<Seance, Integer> implements SeanceRepository {

    public SeanceRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }
}
