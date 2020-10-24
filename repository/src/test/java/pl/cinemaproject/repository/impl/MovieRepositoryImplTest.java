package pl.cinemaproject.repository.impl;

import org.junit.jupiter.api.Test;
import pl.cinemaproject.persistence.model.Movie;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieRepositoryImplTest {


    final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String USERNAME = "hbstudent";
    final String PASSWORD = "hbstudent";


    DatabaseConnector connection = new DatabaseConnector(URL, USERNAME, PASSWORD);

    MovieRepositoryImpl repo = new MovieRepositoryImpl(connection);

    @Test
    void findByTitle() {

        //given

        String moveTitle = "Harry Potter";

        //when

        var result =  repo.findByTitle(moveTitle).orElseThrow();

        //then


        assertNotNull(result);
        assertEquals(moveTitle,result.getTitle());


    }

    //ToDo znaki specjalne

    @Test
    void findByDescription() {

        //given

        String description = "horror movie";

        //when

        var result =  repo.findByDescription(description);

        //then

        result.forEach(System.out::println);

        assertEquals(1,result.size());



    }

    //ToDo Daty jak ogarnąć

    @Test
    void findByStartTime() {

        //given

        Date date = Date.valueOf(LocalDate.of(2020,12,12));


        //when

        var result = repo.findByStartTime(date);

        //then

        result.forEach(System.out::println);

        assertEquals(1,result.size());
    }
}