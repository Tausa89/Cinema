package pl.cinemaproject.service;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.repository.CinemaRepository;
import pl.cinemaproject.repository.CinemaRoomRepository;
import pl.cinemaproject.repository.CityRepository;
import pl.cinemaproject.repository.SeatRepository;

import java.util.List;
import java.util.stream.Collectors;


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

    public int findCinemaIdByName(String cinemaName){

        if(cinemaRepository.findByName(cinemaName).isEmpty()){
            throw new NullPointerException("City " + cinemaName + " dose not exist");
        }

        return cinemaRepository.findByName(cinemaName).map(Cinema::getId).orElseThrow();
    }


    public String addNewCinemaRoom(CinemaRoom cinemaRoom){

        if (cinemaRoomRepository.findByName(cinemaRoom.getName()).isPresent()){
            throw new NullPointerException("City " + cinemaRoom + " dose not exist");
        }

        return cinemaRoomRepository.add(cinemaRoom).orElseThrow().getName();
    }


    public String updateCity(City city){

//        var cityToUpdate = cityRepository.findByName(cityName).orElseThrow();
//        cityToUpdate.setName("New");
        cityRepository.update(city,city.getId());

        return "xxxx";


    }

    public String removeCity(String name){

        var cityToRemove = findCityIdByName(name);
        return "Successfully remove " + cityRepository.deleteById(cityToRemove).orElseThrow().getName();
    }



    //ToDo Zapytaj Krzyska czy tu powinnienem użyć strumini czy żeby zostawić zabawę
    // bazie danych powinno już w sql pobierać tylko tabele z nazwami miast
    public List<String> getAllCitiesNames(){

        return
                cityRepository
                        .findAll()
                        .stream()
                        .map(City::getName)
                        .collect(Collectors.toList());

    }


}
