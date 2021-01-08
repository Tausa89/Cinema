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
        } else
            System.out.print("[C] ");
    }

    private CinemaRoom getCinemaRoomBySeanceId(Integer seanceId) {

        return cinemaRoomRepository.findById(seanceRepository.findById(seanceId).orElseThrow().getCinemaRoomId()).orElseThrow();
    }


    private boolean checkIfSeatIsOpen(int seanceId, int seatId) {

        return seatSeanceRepository.findBySeanceIdAndSeatId(seanceId, seatId).isEmpty();
    }


    public SeancesSeat reserveSeat(int seanceId, int rowNumber, int placeNumber) {


        var cinemaRoomId = getCinemaRoomBySeanceId(seanceId).getId();
        var seat = getSeat(rowNumber, placeNumber, cinemaRoomId);

        if (checkIfSeatIsOpen(seanceId, seat.getId())) {
            var seanceSeatId = seatSeanceRepository.add(SeancesSeat
                    .builder()
                    .seanceId(seanceId)
                    .seatId(seat.getId())
                    .status(Status.RESERVED)
                    .build()).orElseThrow().getId();
            return seatSeanceRepository.findById(seanceSeatId).orElseThrow();
        } else
            return null;


    }


    public SeancesSeat orderSeat(int seanceId, int rowNumber, int placeNumber) {


        var cinemaRoomId = getCinemaRoomBySeanceId(seanceId).getId();
        var seat = getSeat(rowNumber, placeNumber, cinemaRoomId);

        if (checkIfSeatIsOpen(seanceId, seat.getId())) {
            var seanceSeatId = seatSeanceRepository.add(SeancesSeat
                    .builder()
                    .seanceId(seanceId)
                    .seatId(seat.getId())
                    .status(Status.ORDERED)
                    .build()).orElseThrow().getId();
            return seatSeanceRepository.findById(seanceSeatId).orElseThrow();
        } else
            return null;
    }


//    private void checkNextSeatStatus(int rowNumber, int placeNumber, int seanceId){
//
//        var cinemaRoom = getCinemaRoomBySeanceId(seanceId);
//
//        if(placeNumber - 1 < 1){
//
//        }
//        var leftSeatId = getSeat(rowNumber,placeNumber -1,cinemaRoom.getId()).getId();
//        var rightSeatId = getSeat(rowNumber,placeNumber +1,cinemaRoom.getId()).getId();
//        if(checkIfSeatIsOpen(seanceId,))
//
//    }

    private boolean checkIfSeatExist(int rowNumber, int placeNumber, int cinemaRoomId) {


        return seatRepository.findByRowNumberPlaceNumberAndCinemaRoomId(rowNumber, placeNumber, cinemaRoomId).isPresent();
    }


    private int checkWhichSeatExist(int rowNumber, int placeNumber, int cinemaRoomId) {

        var rightSeat = checkIfSeatExist(rowNumber, placeNumber + 1, cinemaRoomId);
        var leftSeat = checkIfSeatExist(rowNumber, placeNumber + -1, cinemaRoomId);

        if (rightSeat && leftSeat) {
            return 0;
        } else if (!rightSeat && leftSeat) {
            return -1;
        } else {
            return 1;
        }
    }

    private void nieWiemCo(int result) {

        switch (result) {
            case 0 -> {

            }
        }

    }


//    public void checkIfNextSeatIsOpen(SeancesSeat seancesSeat){
//        var seat = seatRepository.findById(seancesSeat.getSeatId()).orElseThrow();
//        var rightSeatExist = checkIfSeatExist(seat.getRowAmount(),seat.getPlace() + 1, seat.getCinemaRoomId());
//        var leftSeatExist = checkIfSeatExist(seat.getRowAmount(),seat.getPlace() - 1, seat.getCinemaRoomId());
//
//        var rightSeatId = seatRepository.findByRowNumberPlaceNumberAndCinemaRoomId(seat.getRowAmount(),
//                seat.getPlace() + 1, seat.getCinemaRoomId()).orElseThrow();
//        var leftSeatId = seatRepository.findByRowNumberPlaceNumberAndCinemaRoomId(seat.getRowAmount(),
//                seat.getPlace() - 1, seat.getCinemaRoomId()).orElseThrow();
//
//        if(rightSeatExist && leftSeatExist){
//
//            var isRightSeatOpen = checkIfSeatIsOpen(seancesSeat.getSeanceId(), rightSeatId.getId());
//            var isLeftSeatOpen = checkIfSeatIsOpen(seancesSeat.getSeanceId(), leftSeatId.getId());
//            if(isRightSeatOpen && isLeftSeatOpen){
//                System.out.println(rightSeatId + " and " + leftSeatId);
//            }
//            else if (!isRightSeatOpen && isLeftSeatOpen){
//                System.out.println(leftSeatId);
//            }
//            else if (isRightSeatOpen){
//                System.out.println(rightSeatId);
//            }
//
//            else
//                System.out.println("No more options");
//        }
//        else if (!rightSeatExist && leftSeatExist){
//
//
//        }
//        else {
//
//        }
//
//
//    }


    public void checkIfRightSeatIsFree(SeancesSeat seancesSeat) {

        var seat = getSeatById(seancesSeat.getSeatId());
        var seatNumber = seat.getPlace() + 1;
        if (checkIfNextSeatExist(seat.getRowAmount(), seatNumber, seat.getCinemaRoomId())) {
            var nextAvailableSeat = getSeat(seat.getRowAmount(), seatNumber,seat.getCinemaRoomId());
            if (checkIfSeatIsOpen(seancesSeat.getSeanceId(), nextAvailableSeat.getId())) {
                System.out.println("Next available seat for you is seat in row " + nextAvailableSeat.getRowAmount() +
                        "seat number " + nextAvailableSeat.getPlace());
            } else
                System.out.println("No available seat");
        } else
            System.out.println("There is no more seats");


    }

    public void checkIfLeftSeatIsFree(SeancesSeat seancesSeat) {

        var seat = getSeatById(seancesSeat.getSeatId());
        var seatNumber = seat.getPlace() - 1;
        if (checkIfNextSeatExist(seat.getRowAmount(), seatNumber, seat.getCinemaRoomId())) {
            var nextAvailableSeat = getSeat(seat.getRowAmount(), seatNumber,seat.getCinemaRoomId());
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


//    private boolean checkIfSeatsHaveThisSameId(SeancesSeat seancesSeat, Integer seanceId, Integer seatId){
//
//        var seanceSeat2 = seatSeanceRepository.findBySeanceIdAndSeatId(seanceId,seatId).orElseThrow();
//
//        return seancesSeat.getId() == seanceSeat2.getId();
//    }


}
