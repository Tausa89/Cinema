package pl.cinemaproject.repository.impl;

import org.junit.jupiter.api.Test;
import pl.cinemaproject.repository.SeatSeanceRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import static org.junit.jupiter.api.Assertions.*;

class SeatSeanceRepositoryImplTest {


    final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String USERNAME = "hbstudent";
    final String PASSWORD = "hbstudent";

    DatabaseConnector connection = new DatabaseConnector(URL, USERNAME, PASSWORD);

    SeatSeanceRepositoryImpl repository = new SeatSeanceRepositoryImpl(connection);

    @Test
    void shouldReturnSeat(){

        var seat = repository.findBySeanceIdAndSeatId(1,152).orElseThrow();

        assertNotNull(seat);
        assertEquals(1,seat.getId());
    }


}