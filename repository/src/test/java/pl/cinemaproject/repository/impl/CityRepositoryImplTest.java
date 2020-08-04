package pl.cinemaproject.repository.impl;

import org.junit.jupiter.api.Test;
import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.repository.CityRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CityRepositoryImplTest {



    @Test
    void shouldReturnObjectWithSameName(){

        //given

        var city = City
                .builder()
                .name("City")
                .build();

        CityRepositoryImpl cityRepository = mock(CityRepositoryImpl.class);
        when(cityRepository.findByName("City")).thenReturn(Optional.ofNullable(city));
        //when

        var  returnedCity = cityRepository.findByName("City").orElseThrow();


        //then
        assertNotNull(returnedCity);
        assertEquals("City", returnedCity.getName());

    }

    @Test
    void shouldThrownException(){


        //given

        var city = City
                .builder()
                .name("City")
                .build();

        CityRepositoryImpl cityRepository = mock(CityRepositoryImpl.class);
        when(cityRepository.findByName("name")).thenReturn(null);
        //when

        var  returnedCity = cityRepository.findByName("name");


        //then
        assertNull(returnedCity);



    }


}