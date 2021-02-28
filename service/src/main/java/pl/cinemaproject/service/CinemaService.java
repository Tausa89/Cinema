package pl.cinemaproject.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.enums.Status;
import pl.cinemaproject.persistence.mapper.Mappers;
import pl.cinemaproject.persistence.model.*;
import pl.cinemaproject.persistence.model.view.CinemaCityRoomsView;
import pl.cinemaproject.persistence.modeldto.CinemaComplexDTO;
import pl.cinemaproject.repository.*;
import pl.cinemaproject.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;
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

    private Integer addCity(@NonNull City city) {

        if (cityRepository.findByName(city.getName()).isPresent()) {
            throw new ServiceException("City with name " + city + " already exists");
        }

        return cityRepository
                .add(city)
                .map(City::getId)
                .orElseThrow();
    }

    private Integer addCinema(@NonNull Cinema cinema) {

        if (cinemaRepository.findByName(cinema.getName()).isPresent()) {
            throw new ServiceException("Cinema with name " + cinema.getName() + " already exists");
        }

        return cinemaRepository
                .add(cinema)
                .map(Cinema::getId)
                .orElseThrow();
    }

    private List<CinemaRoom> addCinemaRooms(List<CinemaRoom> cinemaRooms) {

        return new ArrayList<>(cinemaRoomRepository.addAll(cinemaRooms));

    }


    private List<Seat> generateSeats(@NonNull List<CinemaRoom> cinemaRoomsWithIds) {

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

    private Long addSeats(@NonNull List<Seat> seats) {

        return seatRepository
                .addAll(seats)
                .stream()
                .map(Seat::getId).count();

    }


    private List<CinemaRoom> getCinemaRoomsWithIds(@NonNull List<CinemaRoom> cinemaRooms) {

        return addCinemaRooms(cinemaRooms);
    }

    private Integer getCinemaId(Cinema cinema) {

        return addCinema(cinema);
    }

    private Integer getCityId(CinemaCityRoomsView cinemaView) {

        return addCity(cinemaView.getCity());
    }


    public void printCinemaRoomView(@NonNull Integer seanceId) {

        var cinemaRoom = getCinemaRoomBySeanceId(seanceId);
        var numberOfRows = cinemaRoom.getRowsNumber();
        var numberOfSeatProRow = cinemaRoom.getPlaces();

        for (int i = 1; i <= numberOfRows; i++) {
            System.out.println();
            for (int j = 1; j <= numberOfSeatProRow; j++) {

                var seat = getSeat(i, j, cinemaRoom.getId());
                printPlaceDependsOnSeanceSeatStatus(checkIfSeatIsOpen(seanceId, seat.getId()));

            }
        }

    }

    private Seat getSeat(int rowNumber, int placeNumber, int cinemaRoomId) {

        return seatRepository.findByRowNumberPlaceNumberAndCinemaRoomId(rowNumber, placeNumber, cinemaRoomId).orElseThrow();

    }

    private void printPlaceDependsOnSeanceSeatStatus(boolean seanceSeatStatus) {
        if (seanceSeatStatus) {
            System.out.print("[O] ");
        } else {
            System.out.print("[C] ");
        }
    }

    private CinemaRoom getCinemaRoomBySeanceId(Integer seanceId) {

        return cinemaRoomRepository.findById(seanceRepository.findById(seanceId).orElseThrow().getCinemaRoomId()).orElseThrow();
    }


    private boolean checkIfSeatIsOpen(int seanceId, int seatId) {

        return seatSeanceRepository.findBySeanceIdAndSeatId(seanceId, seatId).isEmpty();
    }


    public SeancesSeat prepareSeatForReservation(int seanceId, int rowNumber, int placeNumber) {

        var cinemaRoomId = getCinemaRoomBySeanceId(seanceId).getId();
        var seat = getSeat(rowNumber, placeNumber, cinemaRoomId);

        if (checkIfSeatIsOpen(seanceId, seat.getId())) {

            return SeancesSeat
                    .builder()
                    .seanceId(seanceId)
                    .seatId(seat.getId())
                    .status(Status.RESERVED)
                    .build();
        }
        return null;

    }

    public SeancesSeat prepareSeatForOrder(int seanceId, int rowNumber, int placeNumber) {


        var cinemaRoomId = getCinemaRoomBySeanceId(seanceId).getId();
        var seat = getSeat(rowNumber, placeNumber, cinemaRoomId);

        if (checkIfSeatIsOpen(seanceId, seat.getId())) {
            return SeancesSeat
                    .builder()
                    .seanceId(seanceId)
                    .seatId(seat.getId())
                    .status(Status.ORDERED)
                    .build();
        } else {
            return null;
        }
    }

    public SeancesSeat addSeanceSeat(@NonNull SeancesSeat seancesSeat) {

        var seanceSeatId = seatSeanceRepository.add(seancesSeat).orElseThrow().getId();
        return seatSeanceRepository.findById(seanceSeatId).orElseThrow();
    }


    private boolean checkIfSeatExist(int rowNumber, int placeNumber, int cinemaRoomId) {


        return seatRepository.findByRowNumberPlaceNumberAndCinemaRoomId(rowNumber, placeNumber, cinemaRoomId).isPresent();
    }



    public void checkIfRightSeatIsFree(SeancesSeat seancesSeat) {

        var seat = getSeatById(seancesSeat.getSeatId());
        var seatNumber = seat.getPlace() + 1;

        if (checkIfNextSeatExist(seat.getRowAmount(), seatNumber, seat.getCinemaRoomId())) {
            var nextAvailableSeat = getSeat(seat.getRowAmount(), seatNumber, seat.getCinemaRoomId());

            if (checkIfSeatIsOpen(seancesSeat.getSeanceId(), nextAvailableSeat.getId())) {
                System.out.println("Next available seat for you is seat in row " + nextAvailableSeat.getRowAmount() +
                        "seat number " + nextAvailableSeat.getPlace());
            } else {
                System.out.println("No available seat");
            }
        } else {
            System.out.println("There is no more seats");
        }


    }

    public void checkIfLeftSeatIsFree(SeancesSeat seancesSeat) {

        var seat = getSeatById(seancesSeat.getSeatId());
        var seatNumber = seat.getPlace() - 1;
        if (checkIfNextSeatExist(seat.getRowAmount(), seatNumber, seat.getCinemaRoomId())) {
            var nextAvailableSeat = getSeat(seat.getRowAmount(), seatNumber, seat.getCinemaRoomId());
            if (checkIfSeatIsOpen(seancesSeat.getSeanceId(), nextAvailableSeat.getId())) {
                System.out.println("Next available seat for you is seat in row " + nextAvailableSeat.getRowAmount() +
                        "seat number " + nextAvailableSeat.getPlace());
            } else
                System.out.println("No available seat");
        } else
            System.out.println("There is no more seats");


    }


    private Seat getSeatById(Integer seatId) {
        return seatRepository.findById(seatId).orElseThrow();
    }

    private boolean checkIfNextSeatExist(Integer rowNumber, Integer placeNumber, Integer cinemaRoomId) {

        return seatRepository.findByRowNumberPlaceNumberAndCinemaRoomId(rowNumber, placeNumber, cinemaRoomId).isPresent();
    }




}
