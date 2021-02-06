package pl.cinemaproject.service;

import pl.cinemaproject.persistence.model.view.SeancesView;
import pl.cinemaproject.repository.generic.DatabaseConnector;
import pl.cinemaproject.repository.impl.*;

import java.util.List;

public class TempMain2 {

    public static void main(String[] args) {

        final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        final String USERNAME = "hbstudent";
        final String PASSWORD = "hbstudent";

        var connection = new DatabaseConnector(URL, USERNAME, PASSWORD);

//        final String FILE_ONE = "cinema_data_file.json";
//
//        CinemaDTOJsonConverter cinemaDTOJsonConverter = new CinemaDTOJsonConverter(FILE_ONE);
//
//        CinemaComplexDTO cinemaComplexDTOS = cinemaDTOJsonConverter
//                .fromJson()
//                .orElseThrow();
//
//
        var cityRepo = new CityRepositoryImpl(connection);
        var cinemaRepo = new CinemaRepositoryImpl(connection);
        var cinemaRoomRepo = new CinemaRoomRepositoryImpl(connection);
        var seatRepo = new SeatRepositoryImpl(connection);
        var viewRepo = new SeancesViewImpl(connection);
        var userService = new SeancesService(viewRepo);


        List<SeancesView> seancesList = List.of(
                SeancesView.builder()
                        .cityName("OneCity")
                        .cinemaName("OneCinema")
                        .cinemaRoomName("CinemaRoomOne")
                        .movieTitle("RandomMovie")
                        .dateAndTime("2020-12-01 21:00:00")
                        .build(),
                SeancesView.builder()
                        .cityName("TwoCity")
                        .cinemaName("TwoCinema")
                        .cinemaRoomName("CinemaRoomTwo")
                        .movieTitle("RareMovie")
                        .dateAndTime("2020-10-02 19:30:00")
                        .build(),
                SeancesView.builder()
                        .cityName("ThreeCity")
                        .cinemaName("ThreeCinema")
                        .cinemaRoomName("CinemaRoomThree")
                        .movieTitle("EpicMovie")
                        .dateAndTime("2020-12-20 15:00:00")
                        .build());

        var resoult = userService.convertSeancesToListOfString();

        resoult.forEach(System.out::println);
//
//
//        CinemaService cinemaService = new CinemaService(cityRepo,cinemaRepo,cinemaRoomRepo,seatRepo);
//        cinemaService.createNewCinemaComplex(cinemaComplexDTOS);
    }
}
