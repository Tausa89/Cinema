package pl.cinemaproject.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.repository.CinemaRepository;
import pl.cinemaproject.repository.CinemaRoomRepository;
import pl.cinemaproject.repository.CityRepository;
import pl.cinemaproject.repository.SeatRepository;
import pl.cinemaproject.service.exception.AdminServiceException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class AdminService {


    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;
    private final SeatRepository seatRepository;


    public String addNewCity(@NonNull String cityName) {


        if (cityRepository.findByName(cityName).isPresent()) {
            throw new AdminServiceException(cityName + " already exists in database");
        }

        return cityRepository
                .add(City
                        .builder()
                        .name(cityName)
                        .build())
                .orElseThrow()
                .getName();

    }


    public int findCityIdByName(@NonNull String cityName) {

        if (cityRepository.findByName(cityName).isEmpty()) {
            throw new AdminServiceException(cityName + " dose not exist in data base");
        }

        return cityRepository.findByName(cityName).map(City::getId).orElseThrow();

    }


    public String addNewCinema(@NonNull Cinema cinema) {

        if (cinemaRepository.findByName(cinema.getName()).isPresent()) {
            throw new AdminServiceException(cinema.getName() + " already exists in data base");
        }

        return cinemaRepository.add(cinema).orElseThrow().getName();
    }

    public int findCinemaIdByName(@NonNull String cinemaName) {

        if (cinemaRepository.findByName(cinemaName).isEmpty()) {
            throw new AdminServiceException(cinemaName + " dose not exist in data base");
        }

        return cinemaRepository.findByName(cinemaName).map(Cinema::getId).orElseThrow();
    }

    public int findCinemaRoomIdByName(@NonNull String cinemaRoomName, @NonNull String cinemaName) {

        var cinemaId = findCinemaIdByName(cinemaName);

        return cinemaRoomRepository.findCinemaRoomByNameWithCinemaId(cinemaRoomName, cinemaId).orElseThrow().getId();

    }


    public String addNewCinemaRoom(@NonNull CinemaRoom cinemaRoom) {

        if (cinemaRoomRepository.findCinemaRoomByNameWithCinemaId(cinemaRoom.getName(),
                cinemaRoom.getCinemaId()).isPresent()) {
            throw new AdminServiceException(cinemaRoom.getName() + " dose already exist");
        }

        return cinemaRoomRepository.add(cinemaRoom).orElseThrow().getName();
    }


    public String updateCity(@NonNull City city) {

        if(cityRepository.findByName(city.getName()).isEmpty()){
         throw new AdminServiceException(city.getName() + "dose not exist in data base");

        }
        return cityRepository.update(city, city.getId()).orElseThrow().getName();


    }

    public String updateCinema(@NonNull Cinema cinema) {

        if(cinemaRepository.findByName(cinema.getName()).isEmpty()){
            throw new AdminServiceException(cinema.getName() + "dose not exist in data base");

        }
        return "New cinema name " + cinemaRepository.update(cinema, cinema.getId()).orElseThrow().getName();


    }

    public String removeCity(@NonNull String cityName) {

        if (cityRepository.findByName(cityName).isEmpty()){
            throw new AdminServiceException(cityName + "dose not exist in data base");
        }
        var cityToRemove = findCityIdByName(cityName);
        return "Successfully remove " + cityRepository.deleteById(cityToRemove).orElseThrow().getName();
    }


    public String removeCinema(String cinemaName) {

        if (cinemaRepository.findByName(cinemaName).isEmpty()){
            throw new AdminServiceException(cinemaName + "dose not exist in data base");
        }
        var cinemaToRemove = findCinemaIdByName(cinemaName);
        return "Successfully remove " + cinemaRepository.deleteById(cinemaToRemove).orElseThrow().getName();
    }


    public String removeCinemaRoom(String cinemaRoomName, String cinemaName) {

        if (findCinemaRoomIdByName(cinemaRoomName,cinemaName) == 0){
            throw new AdminServiceException(cinemaRoomName + "dose not exist in data base");
        }

        var cinemaRoomToRemove = findCinemaRoomIdByName(cinemaRoomName, cinemaName);

        return "Successfully remove " + cinemaRoomRepository.deleteById(cinemaRoomToRemove).orElseThrow().getName();
    }


    //ToDo Zapytaj Krzyska czy tu powinnienem użyć strumini czy żeby zostawić zabawę
    // bazie danych powinno już w sql pobierać tylko tabele z nazwami miast
    public List<String> getAllCitiesNames() {

        return
                cityRepository
                        .findAll()
                        .stream()
                        .map(City::getName)
                        .collect(Collectors.toList());

    }

    public Cinema getCinemaByName(String cinemaName) {

        return cinemaRepository.findByName(cinemaName).orElseThrow();
    }

    public List<String> getAllCinemasNames() {

        return cinemaRepository
                .findAll()
                .stream()
                .map(Cinema::getName)
                .collect(Collectors.toList());
    }


    public List<String> getAllCinemaRoomsForOneCinema(int id) {

        return cinemaRoomRepository
                .findAllCinemaRoomsByCinemaId(id)
                .stream()
                .map(CinemaRoom::getName)
                .collect(Collectors.toList());

    }

    public CinemaRoom getCinemaRoomByName(@NonNull String cinemaRoomName) {

        return cinemaRoomRepository
                .findByName(cinemaRoomName)
                .orElseThrow();
    }

    public String updateCinemaRoom(@NonNull CinemaRoom cinemaRoom) {

        if (cinemaRoomRepository.findCinemaRoomByNameWithCinemaId(cinemaRoom.getName(),
                cinemaRoom.getCinemaId()).isEmpty()){
            throw new AdminServiceException(cinemaRoom.getName() + "dose not exist in data base");
        }
        return "New cinema room name is " + cinemaRoomRepository.update(cinemaRoom, cinemaRoom.getId()).orElseThrow().getName();
    }


}
