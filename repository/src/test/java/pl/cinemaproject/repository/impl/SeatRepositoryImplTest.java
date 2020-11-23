package pl.cinemaproject.repository.impl;

import org.junit.jupiter.api.Test;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import static org.junit.jupiter.api.Assertions.*;

class SeatRepositoryImplTest {



    final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String USERNAME = "hbstudent";
    final String PASSWORD = "hbstudent";


    DatabaseConnector connection = new DatabaseConnector(URL, USERNAME, PASSWORD);

    SeatRepositoryImpl repository = new SeatRepositoryImpl(connection);


    @Test
    void shouldReturnSeat(){

        var seat = repository.findByRowNumberPlaceNumberAndCinemaRoomId(1,1,41).orElseThrow();

        System.out.println(seat.getId());

        assertEquals(148,seat.getId());

    }

}