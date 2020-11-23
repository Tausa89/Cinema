package pl.cinemaproject.service;

import org.junit.jupiter.api.Test;
import pl.cinemaproject.repository.*;
import pl.cinemaproject.repository.generic.DatabaseConnector;
import pl.cinemaproject.repository.impl.*;

import static org.junit.jupiter.api.Assertions.*;

class CinemaServiceTest {


    final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String USERNAME = "hbstudent";
    final String PASSWORD = "hbstudent";


    DatabaseConnector connection = new DatabaseConnector(URL, USERNAME, PASSWORD);


    CityRepository cityRepository = new CityRepositoryImpl(connection);
    CinemaRepository cinemaRepository = new CinemaRepositoryImpl(connection);
    CinemaRoomRepository cinemaRoomRepository = new CinemaRoomRepositoryImpl(connection);
    SeatRepository seatRepository = new SeatRepositoryImpl(connection);
    SeatSeanceRepository seatSeanceRepository = new SeatSeanceRepositoryImpl(connection);
    SeanceRepository seanceRepository = new SeanceRepositoryImpl(connection);


    CinemaService cinemaService = new CinemaService(cityRepository,cinemaRepository,cinemaRoomRepository,seatRepository,seatSeanceRepository,seanceRepository);




    @Test
    void shouldPrintCinemaRoom(){


        cinemaService.printCinemaRoomView(1);



    }

}