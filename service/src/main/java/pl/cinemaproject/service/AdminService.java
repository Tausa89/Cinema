package pl.cinemaproject.service;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.repository.CinemaRepository;
import pl.cinemaproject.repository.CinemaRoomRepository;
import pl.cinemaproject.repository.CityRepository;
import pl.cinemaproject.repository.SeatRepository;


@RequiredArgsConstructor
public class AdminService {



    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;
    private final SeatRepository seatRepository;





    public String addNewCity(String cityName){

        if(cityRepository.findByName(cityName).isPresent()){
            throw new NullPointerException("City " + cityName + " already exists");
        }

        return cityRepository
                .add(City
                        .builder()
                        .name(cityName)
                        .build())
                .orElseThrow()
                .getName();

    }


    public int findCityIdByName(String cityName) {

        if(cityRepository.findByName(cityName).isEmpty()){
            throw new NullPointerException("City " + cityName + " dose not exist");
        }

        return cityRepository.findByName(cityName).map(City::getId).orElseThrow();

    }


    public String addNewCinema(Cinema cinema){

        if(cinemaRepository.findByName(cinema.getName()).isPresent()){
            throw new NullPointerException("Cinema " + cinema.getName() + " already exists");
        }

        return cinemaRepository.add(cinema).orElseThrow().getName();
    }

    public int findCinemaByName(){

    }


    public String addNewCinemaRoom(CinemaRoom cinemaRoom){

        if ()
    }
}
