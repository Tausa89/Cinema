package pl.cinemaproject.repository.impl;

import org.junit.jupiter.api.Test;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import static org.junit.jupiter.api.Assertions.*;

class SeancesViewImplTest {

    final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String USERNAME = "hbstudent";
    final String PASSWORD = "hbstudent";


    DatabaseConnector connection = new DatabaseConnector(URL, USERNAME, PASSWORD);

    SeancesViewImpl repo = new SeancesViewImpl(connection);


    @Test
    void shouldReturnList(){

        //given

        //when

        var result = repo.getAllSeancesWithAllDate();

        result.forEach(System.out::println);

        //then
        assertNotNull(result);


    }

    @Test
    void shouldReturnListForGivenCity(){

        var result = repo.getAllSeancesForGivenCity("Wrocław");

        result.forEach(System.out::println);

        assertNotNull(result);

    }

    @Test
    void  shouldReturnListForGivenCinema(){

        var result = repo.getAllSeancesForGivenCinema("Multikino");

        result.forEach(System.out::println);

        assertNotNull(result);
    }

}