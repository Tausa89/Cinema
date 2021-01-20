package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.Ticket;
import pl.cinemaproject.repository.generic.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket,Integer> {


}
