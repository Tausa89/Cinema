package pl.cinemaproject.service;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.enums.Status;
import pl.cinemaproject.persistence.mapper.Mappers;
import pl.cinemaproject.persistence.model.*;
import pl.cinemaproject.persistence.model.view.CinemaCityRoomsView;
import pl.cinemaproject.persistence.modeldto.CinemaComplexDTO;
import pl.cinemaproject.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class CinemaService {


    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;
    private final SeatRepository seatRepository;
    private final SeatSeanceRepository seatSeanceRepository;
    private final SeanceRepository seanceRepository;


    public Long createNewCinemaComplex(CinemaComplexDTO cinemaComplexDTO) {

        var cinemaView = Mappers.fromDtoToCinemaComplex(cinemaComplexDTO);

        var cityId = getCityId(cinemaView);
        System.out.println("City created. Id = " + cityId);

        var cinema = cinemaView.getCinema();
        cinema.setCityId(cityId);
        var cinemaId = getCinemaId(cinema);
        System.out.println("Cinema created. Id = " + cinemaId);

        var cinemaRooms = cinemaView.getCinemaRooms();
        cinemaRooms.forEach(cinemaRoom -> cinemaRoom.setCinemaId(cinemaId));
        var cinemaRoomsWithIds = getCinemaRoomsWithIds(cinemaRooms);

        var seats = generateSeats(cinemaRoomsWithIds);

        System.out.println(seats);

        var insertedSeats = addSeats(seats);
        System.out.println("Seats created. Number of created seats: " + insertedSeats);

        return insertedSeats;

    }

    private Integer addCity(City city) {

        if (cityRepository.findByName(city.getName()).isPresent()) {
            throw new NullPointerException("City with name " + city + " already exists");
        }

        return cityRepository
                .add(city)
                .map(City::getId)
                .orElseThrow(() -> new IllegalStateException("....."));
    }

    private Integer addCinema(Cinema cinema) {

        if (cinemaRepository.findByName(cinema.getName()).isPresent()) {
            throw new NullPointerException("Cinema with name " + cinema.getName() + " already exists");
        }

        return cinemaRepository
                .add(cinema)
                .map(Cinema::getId)
                .orElseThrow(() -> new IllegalStateException("....."));
    }

    private List<CinemaRoom> addCinemaRooms(List<CinemaRoom> cinemaRooms) {

        return new ArrayList<>(cinemaRoomRepository.addAll(cinemaRooms));

    }


    private List<Seat> generateSeats(List<CinemaRoom> cinemaRoomsWithIds) {

        return cinemaRoomsWithIds
                .stream()
                .flatMap(cinemaRoom -> IntStream
                        .rangeClosed(1, cinemaRoom.getRowsNumber())
                        .boxed()
                        .flatMap(rowNumber -> IntStream
                                .rangeClosed(1, cinemaRoom.getPlaces())
                                .boxed()
                                .map(seatNumber -> Seat
                                        .builder()
                                        .cinemaRoomId(cinemaRoom.getId())
                                        .rowAmount(rowNumber)
                                        .place(seatNumber)
                                        .build())))
                .collect(Collectors.toList());
    }

    private Long addSeats(List<Seat> seats) {

        return seatRepository
                .addAll(seats)
                .stream()
                .map(Seat::getId).count();

    }


    private List<CinemaRoom> getCinemaRoomsWithIds(List<CinemaRoom> cinemaRooms) {

        return addCinemaRooms(cinemaRooms);
    }

    private Integer getCinemaId(Cinema cinema) {

        return addCinema(cinema);
    }

    private Integer getCityId(CinemaCityRoomsView cinemaView) {

        return addCity(cinemaView.getCity());
    }




    public void printCinemaRoomView(int seanceId) {

        var cinemaRoom = getCinemaRoomBySeanceId(seanceId);
        var numberOfRows = cinemaRoom.getRowsNumber();
        var numberOfSeatProRow = cinemaRoom.getPlaces();

        for (int i = 1; i <= numberOfRows; i++) {
            System.out.println("");
            for (int j = 1; j <= numberOfSeatProRow; j++) {

                var seat = seatRepository.findByRowNumberPlaceNumberAndCinemaRoomId(i, j, cinemaRoom.getId()).orElseThrow();
                printPlaceDependsOnSeanceSeatStatus(checkIfSeatIsOpen(seanceId,seat.getId()));

            }
        }

    }

    private void printPlaceDependsOnSeanceSeatStatus(boolean seanceSeatStatus) {
        if (seanceSeatStatus) {
            System.out.print("[O] ");
        } else
            System.out.print("[C] ");
    }

    private CinemaRoom getCinemaRoomBySeanceId(Integer seanceId) {

        return cinemaRoomRepository.findById(seanceRepository.findById(seanceId).orElseThrow().getCinemaRoomId()).orElseThrow();
    }


    private boolean checkIfSeatIsOpen(int seanceId, int seatId){

        return seatSeanceRepository.findBySeanceIdAndSeatId(seanceId, seatId).isEmpty();
    }





}
