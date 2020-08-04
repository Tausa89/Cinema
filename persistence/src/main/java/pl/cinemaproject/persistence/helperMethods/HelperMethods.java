package pl.cinemaproject.persistence.helperMethods;

import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.persistence.model.Seat;

public class HelperMethods {


    public static Seat[][] generateSeats(CinemaRoom cinemaRoom){

        Seat[][] room = new Seat[cinemaRoom.getRows()][cinemaRoom.getSeats()];

//        Seat [][] rooms = Arrays
//                .stream(room)
//                .map(p -> Arrays.stream(p)
//                        .map(s -> Seat
//                                .builder()
//                                .row()
//                                .place()
//                                .build())
//                        .toArray(Seat[]::new))
//                .toArray(Seat[][]::new);

        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                room[i][j] = Seat
                        .builder()
                        .row(i)
                        .place(j)
                        .build();
            }
        }

        return room;
    }
}
