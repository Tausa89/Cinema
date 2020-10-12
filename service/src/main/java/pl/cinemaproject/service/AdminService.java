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

    public int findCinemaRoomIdByName(String cinemaRoomName, String cinemaName){

        var cinemaId = findCinemaIdByName(cinemaName);

        return cinemaRoomRepository.findCinemaRoomByNameWithCinemaId(cinemaRoomName,cinemaId).orElseThrow().getId();

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
        return cityRepository.update(city,city.getId()).orElseThrow().getName();


    }

    public String updateCinema(Cinema cinema){


        return "New cinema name " + cinemaRepository.update(cinema, cinema.getId()).orElseThrow().getName();


    }

    public String removeCity(String cityName){

        var cityToRemove = findCityIdByName(cityName);
        return "Successfully remove " + cityRepository.deleteById(cityToRemove).orElseThrow().getName();
    }


    public String removeCinema(String cinemaName) {

        var cinemaToRemove = findCinemaIdByName(cinemaName);
        return "Successfully remove " + cinemaRepository.deleteById(cinemaToRemove).orElseThrow().getName();
    }


    public String removeCinemaRoom(String cinemaRoomName, String cinemaName) {

        var cinemaRoomToRemove = findCinemaRoomIdByName(cinemaRoomName,cinemaName);

        return "Successfully remove " + cinemaRoomRepository.deleteById(cinemaRoomToRemove).orElseThrow().getName();
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

    public Cinema getCinemaByName(String cinemaName){

        return cinemaRepository.findByName(cinemaName).orElseThrow();
    }

    public List<String> getAllCinemasNames(){

        return cinemaRepository
                .findAll()
                .stream()
                .map(Cinema::getName)
                .collect(Collectors.toList());
    }


    public List<String> getAllCinemaRoomsForOneCinema(int id){

        return cinemaRoomRepository
                .findAllCinemaRoomsByCinemaId(id)
                .stream()
                .map(CinemaRoom::getName)
                .collect(Collectors.toList());

    }

    public CinemaRoom getCinemaRoomByName(String cinemaRoomName){

        return cinemaRoomRepository
                .findByName(cinemaRoomName)
                .orElseThrow();
    }

    public String updateCinemaRoom(CinemaRoom cinemaRoom) {


        return "New cinema room name is " + cinemaRoomRepository.update(cinemaRoom, cinemaRoom.getId()).orElseThrow().getName();
    }


}
