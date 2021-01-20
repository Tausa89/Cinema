package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.Ticket;
import pl.cinemaproject.repository.TicketRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

public class TicketRepositoryImpl extends AbstractCrudRepository<Ticket, Integer> implements TicketRepository {

    public TicketRepositoryImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }
}
