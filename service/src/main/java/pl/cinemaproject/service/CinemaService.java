package pl.cinemaproject.service;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.mapper.Mappers;
import pl.cinemaproject.persistence.model.Cinema;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class CinemaService {


    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;
    private final SeatRepository seatRepository;

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


    private List<CinemaRoom> addCinemaRooms(List<CinemaRoom> cinemaRooms){

        return new ArrayList<>(cinemaRoomRepository.addAll(cinemaRooms));

    }

    private List<Integer> addSeats(List<Seat> seats){

        return seatRepository
                .addAll(seats)
                .stream()
                .map(Seat::getId)
                .collect(Collectors.toList());

    }

    public int createNewCinemaComplex(CinemaComplexDTO cinemaComplexDTO) {

        var cinemaView = Mappers.fromDtoToCinemaComplex(cinemaComplexDTO);

        var cityId = addCity(cinemaView.getCity());
        System.out.println("City created. Id = " + cityId);

        var cinema = cinemaView.getCinema();
        cinema.setCityId(cityId);
        var cinemaId = addCinema(cinema);
        System.out.println("Cinema created. Id = " + cinemaId);

        var cinemaRooms = cinemaView.getCinemaRooms();
        cinemaRooms.forEach(cinemaRoom -> cinemaRoom.setCinemaId(cinemaId));
        var cinemaRooms2 = addCinemaRooms(cinemaRooms);
//        List<Integer> cinemaRoomsIds = addCinemaRooms(cinemaRooms);

//        cinemaRoomsIds.forEach(System.out::println);

//        var cinemaRooms = cinemaRoomRepository.addAll(cinemaView
//                .getCinemaRooms()
//                .stream()
//                .map(cinemaRoom -> {
//                    cinemaRoom.setCinemaId(cinemaId);
//                    return cinemaRoom;
//                }).collect(Collectors.toList()));
//        System.out.println("Cinema rooms created. Names = " + cinemaRooms.stream().map(CinemaRoom::getName).collect(Collectors.joining(", ")));

        System.out.println(cinemaRooms);

        cinemaRooms.forEach(cinemaRoom -> System.out.println(cinemaRoom.getId()));

        var seats = generateSeats(cinemaRooms2);

        System.out.println(seats);
        var insertedSeats = seatRepository.addAll(seats);
        System.out.println("Seats created. Number of created seats: " + insertedSeats.size());

        return insertedSeats.size();

        /*if (cityRepository.findByName(cinemaComplex.getCity().getName()).isPresent()) {
            throw new NullPointerException("There is already this City");
        }

        cityRepository.add(cinemaComplex.getCity());

        var cityId = cityRepository.findByName(cinemaComplex.getCinema().getName()).orElseThrow().getId();*/

        /*if (cinemaRepository.findByName(cinemaComplex.getCinema().getName()).isPresent()) {
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

        }*/

    }

    private List<Seat> generateSeats(List<CinemaRoom> cinemaRooms2) {

        return cinemaRooms2
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

}
