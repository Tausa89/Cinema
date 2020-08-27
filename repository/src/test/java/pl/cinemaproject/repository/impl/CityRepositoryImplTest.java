package pl.cinemaproject.repository.impl;

import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Test;
import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.repository.CityRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CityRepositoryImplTest {

    @Test
    void CityRepositoryIntegrationTests() {

        //given
        final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        final String USERNAME = "hbstudent";
        final String PASSWORD = "hbstudent";

        var connection = new DatabaseConnector(URL, USERNAME, PASSWORD);

        var cityRepo = new CityRepositoryImpl(connection);
        var cityName = java.util.UUID.randomUUID().toString();

        //when

        var id = cityRepo.findByName(cityName).orElseThrow().getId();

        var response = cityRepo.findById(id);

        cityRepo.deleteById(id);

        var deletedResponse = cityRepo.findByName(cityName);

        //then
        assertNotNull(response);
        assertNotNull(id);
        assertTrue(deletedResponse.isEmpty());
    }

    @Test
    void shouldReturnObjectWithSameName() {

        //given

        var city = City
                .builder()
                .name("City")
                .build();

        CityRepositoryImpl cityRepository = mock(CityRepositoryImpl.class);
        when(cityRepository.findByName("City")).thenReturn(Optional.ofNullable(city));
        //when

        var returnedCity = cityRepository.findByName("City").orElseThrow();


        //then
        assertNotNull(returnedCity);
        assertEquals("City", returnedCity.getName());

    }

    @Test
    void shouldThrownException() {


        //given

        var city = City
                .builder()
                .name("City")
                .build();

        CityRepositoryImpl cityRepository = mock(CityRepositoryImpl.class);
        when(cityRepository.findByName("name")).thenReturn(null);
        //when

        var returnedCity = cityRepository.findByName("name");


        //then
        assertNull(returnedCity);


    }


}