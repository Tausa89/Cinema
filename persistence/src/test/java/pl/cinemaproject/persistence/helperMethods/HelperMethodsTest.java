package pl.cinemaproject.persistence.helperMethods;

import org.junit.jupiter.api.Test;
import pl.cinemaproject.persistence.model.CinemaRoom;

import static org.junit.jupiter.api.Assertions.*;

class HelperMethodsTest {

    @Test
    void shouldGenerateSeats() {

        var cinemaRoom = CinemaRoom
                .builder()
                .rows(10)
                .seats(10)
                .name("One")
                .build();


        var Seats = HelperMethods.generateSeats(cinemaRoom);

        assertNotNull(Seats[0][1]);

    }
}