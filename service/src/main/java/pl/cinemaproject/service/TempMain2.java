package pl.cinemaproject.service;

import pl.cinemaproject.persistence.converter.CinemaDTOJsonConverter;
import pl.cinemaproject.persistence.modeldto.CinemaComplexDTO;
import pl.cinemaproject.repository.CityRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;
import pl.cinemaproject.repository.impl.CinemaRepositoryImpl;
import pl.cinemaproject.repository.impl.CinemaRoomRepositoryImpl;
import pl.cinemaproject.repository.impl.CityRepositoryImpl;
import pl.cinemaproject.repository.impl.SeatRepositoryImpl;

public class TempMain2 {

    public static void main(String[] args) {

        final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        final String USERNAME = "hbstudent";
        final String PASSWORD = "hbstudent";

        var connection = new DatabaseConnector(URL, USERNAME, PASSWORD);

        final String FILE_ONE = "cinema_data_file.json";

        CinemaDTOJsonConverter cinemaDTOJsonConverter = new CinemaDTOJsonConverter(FILE_ONE);

        CinemaComplexDTO cinemaComplexDTOS = cinemaDTOJsonConverter
                .fromJson()
                .orElseThrow();


        var cityRepo = new CityRepositoryImpl(connection);
        var cinemaRepo = new CinemaRepositoryImpl(connection);
        var cinemaRoomRepo = new CinemaRoomRepositoryImpl(connection);
        var seatRepo = new SeatRepositoryImpl(connection);


        CinemaService cinemaService = new CinemaService(cityRepo,cinemaRepo,cinemaRoomRepo,seatRepo);
        cinemaService.createNewCinemaComplex(cinemaComplexDTOS);
    }
}
