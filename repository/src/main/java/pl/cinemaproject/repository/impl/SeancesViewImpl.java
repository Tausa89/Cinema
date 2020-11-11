package pl.cinemaproject.repository.impl;

import pl.cinemaproject.persistence.model.view.SeancesView;
import pl.cinemaproject.repository.SeancesViewRepository;
import pl.cinemaproject.repository.generic.AbstractCrudRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.List;

public class SeancesViewImpl extends AbstractCrudRepository<SeancesView, Integer>
        implements SeancesViewRepository {

    private final String SQL = """
            Select m.title as movieTitle, s.start_date_time as dateAndTime, cr.name as cinemaRoomName, c.name as cinemaName, ci.name as cityName
            from seances s
            join movies m on s.movie_id = m.id
            join cinema_rooms cr on s.cinema_room_id = cr.id
            join  cinemas c on cr.cinema_id = c.id
            join cities ci on c.city_id = ci.id
            """;


    public SeancesViewImpl(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    @Override
    public List<SeancesView> getAllSeancesWithAllDate() {


        return jdbi.withHandle(handle -> handle
                .createQuery(SQL)
                .mapToBean(SeancesView.class)
                .list());


    }


    @Override
    public List<SeancesView> getAllSeancesForGivenCity(String cityName) {

        final String CITY_SQL = SQL + " where ci.name = :city";


        return jdbi.withHandle(handle ->
                handle
                        .createQuery(CITY_SQL)
                        .bind("city", cityName)
                        .mapToBean(SeancesView.class)
                        .list());
    }


    @Override
    public List<SeancesView> getAllSeancesForGivenCinema(String cinemaName) {

        final String CINEMA_SQL = SQL + " where c.name = :cinema";
        return jdbi.withHandle(handle ->
                handle
                        .createQuery(CINEMA_SQL)
                        .bind("cinema", cinemaName)
                        .mapToBean(SeancesView.class)
                        .list());
    }
}
