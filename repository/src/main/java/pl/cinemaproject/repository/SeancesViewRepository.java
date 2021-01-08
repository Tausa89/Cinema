package pl.cinemaproject.repository;

import pl.cinemaproject.persistence.model.view.SeancesView;
import pl.cinemaproject.repository.generic.CrudRepository;

import java.util.List;

public interface SeancesViewRepository extends CrudRepository <SeancesView, Integer> {


    List<SeancesView> getAllSeancesWithAllDate();

    List<SeancesView> getAllSeancesForGivenCity(String cityName);

    List<SeancesView> getAllSeancesForGivenCinema(String cinemaName);

    List<SeancesView> getAllSeancesForGivenMovie(String movieName);
}
