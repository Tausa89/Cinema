package pl.cinemaproject.service;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.helperMethods.HelperMethods;
import pl.cinemaproject.persistence.mapper.Mappers;
import pl.cinemaproject.persistence.model.CinemaComplex;
import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.persistence.model.Seat;
import pl.cinemaproject.persistence.modeldto.CinemaComplexDTO;
import pl.cinemaproject.repository.CinemaRepository;
import pl.cinemaproject.repository.CinemaRoomRepository;
import pl.cinemaproject.repository.CityRepository;
import pl.cinemaproject.repository.SeatRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CinemaService {


    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;
    private final SeatRepository seatRepository;





    public void createNewCinemaComplex(CinemaComplexDTO cinemaComplexDTO) {


        var cinemaComplex = Mappers.fromDtoToCinemaComplex(cinemaComplexDTO);


        if (cityRepository.findByName(cinemaComplex.getCity().getName()).isPresent()) {
            throw new NullPointerException("There is already this City");
        }

        cityRepository.add(cinemaComplex.getCity());

        var cityId = cityRepository.findByName(cinemaComplex.getCinema().getName()).orElseThrow().getId();

        if (cinemaRepository.findByName(cinemaComplex.getCinema().getName()).isPresent()) {
            throw new NullPointerException("There is already this Cinema");
        }

        var cinema = cinemaComplex.getCinema();
        cinema.setCityId(cityId);
        cinemaRepository.add(cinema);


        cinemaComplex.getCinemaRooms().forEach(p ->
        {
            if (cinemaRoomRepository.findByName(p.getName()).isPresent()) {
                throw new NullPointerException("There is already this CinemaRoom");
            }
        });

        var cinemaId = cinemaRepository.findByName(cinemaComplex.getCinema().getName()).orElseThrow().getId();

        for (CinemaRoom cinemaRoom: cinemaComplex.getCinemaRooms()){
            cinemaRoom.setCinemaId(cinemaId);
            cinemaRoomRepository.add(cinemaRoom);
        }

        List<CinemaRoom> cinemaRoomList = new ArrayList<>();
        for(CinemaRoom cinemaRoom: cinemaComplex.getCinemaRooms()){
            cinemaRoomList.add(cinemaRoomRepository.findByName(cinemaRoom.getName()).orElseThrow());
        }


        for (CinemaRoom cinemaRoom: cinemaRoomList){
            var seats = HelperMethods.generateSeats(cinemaRoom);
            //            for (int i = 0; i < seats.length; i++) {
//                for (int j = 0; j < seats[i].length; j++) {
//                    seats[i][j].setCinemaRoomId(cinemaRoom.getCinemaId());
//                }
//            }

            for (Seat[] seat : seats) {
                for (Seat value : seat) {
                    value.setCinemaRoomId(cinemaRoom.getCinemaId());
                }
            }

        }



    }

}
