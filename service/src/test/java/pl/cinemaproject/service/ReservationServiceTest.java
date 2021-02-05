package pl.cinemaproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;


    @Test
    void shouldReturnReservationNumber(){


        var reservationNumber = reservationService.reservationNumberGenerator();
        System.out.println(reservationNumber);

        assertNotNull(reservationNumber);
        assertEquals(12,reservationNumber.length());
    }

}