package pl.cinemaproject.persistence.mapper;

import org.junit.jupiter.api.Test;
import pl.cinemaproject.persistence.model.CinemaComplex;
import pl.cinemaproject.persistence.modeldto.CinemaComplexDTO;
import pl.cinemaproject.persistence.modeldto.CinemaRoomDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MappersTest {


    @Test
    void shouldConvertCinemaComplexDtoToCinemaComplex(){

        //given
        List<CinemaRoomDTO> cinemaRoomDtos = List.of(CinemaRoomDTO
                        .builder()
                        .name("1")
                        .rows(10)
                        .seats(20)
                        .build(),
                CinemaRoomDTO
                        .builder()
                        .name("2")
                        .rows(20)
                        .seats(10)
                        .build());

        var cinemaComplexDto = CinemaComplexDTO
                .builder()
                .cinemaName("ExampleName")
                .cityName("City")
                .cinemaRooms(cinemaRoomDtos)
                .build();

        //when
        var cinemaComplex = Mappers.fromDtoToCinemaComplex(cinemaComplexDto);

        //then
        assertNotNull(cinemaComplex);
        assertNotNull(cinemaComplex.getCinema());
        assertNotNull(cinemaComplex.getCinemaRooms());
        assertNotNull(cinemaComplex.getCity());

    }

}