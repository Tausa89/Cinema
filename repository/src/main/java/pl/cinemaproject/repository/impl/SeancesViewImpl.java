package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.view.SeancesView;
import pl.cinemaproject.repository.SeancesViewRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.List;

public class SeancesViewImpl extends AbstractCrudRepository <SeancesView, Integer>
        implements SeancesViewRepository {


    public SeancesViewImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    @Override
    public List<SeancesView> findCitiesWithCinemasAndCinemaRooms() {

        final String SQL = """
                Select m.title as movieTitle, s.start_date_time as dateAndTime, cr.name as cinemaRoomName, c.name as cinemaName, ci.name as cityName
                from seances s
                join movies m on s.movie_id = m.id
                join cinema_rooms cr on s.cinema_room_id = cr.id
                join  cinemas c on cr.cinema_id = c.id
                join cities ci on c.city_id = ci.id
                """;


        return  jdbi.withHandle(handle -> handle
                .createQuery(SQL)
                .mapToBean(SeancesView.class)
                .list());


    }
}
