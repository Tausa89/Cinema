package pl.cinemaproject.persistence.mapper;

import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.persistence.model.view.CinemaCityRoomsView;
import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.persistence.modeldto.CinemaComplexDTO;

import java.util.List;
import java.util.stream.Collectors;

public class Mappers {



    public static CinemaCityRoomsView fromDtoToCinemaComplex(CinemaComplexDTO cinemaComplexDTO){

        return CinemaCityRoomsView
                .builder()
                .cinema(Cinema
                        .builder()
                        .name(cinemaComplexDTO.getCinemaName())
                        .build())
                .city(City
                        .builder()
                        .name(cinemaComplexDTO.getCityName())
                        .build())
                .cinemaRooms(fromDtoToCinemaRoom(cinemaComplexDTO))
                .build();
    }



    private static List<CinemaRoom> fromDtoToCinemaRoom (CinemaComplexDTO cinemaComplexDTO){

        return cinemaComplexDTO.getCinemaRooms().stream().map(p -> CinemaRoom
                .builder()
                .name(p.getName())
                .rowsNumber(p.getRows())
                .places(p.getSeats())
                .build())
                .collect(Collectors.toList());

    }
}
