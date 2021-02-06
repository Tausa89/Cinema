package pl.cinemaproject.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.Reservation;
import pl.cinemaproject.persistence.model.SeancesSeat;
import pl.cinemaproject.repository.ReservationRepository;


import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
public class ReservationService {



    private final ReservationRepository reservationRepository;





    public Reservation generateReservation(@NonNull SeancesSeat seancesSeat){

        return Reservation
                .builder()
                .seanceId(seancesSeat.getSeanceId())
                .seatId(seancesSeat.getSeatId())
                .reservationNumber(reservationNumberGenerator())
                .build();

    }


    public String addReservation(@NonNull Reservation reservation){


        return reservationRepository.add(reservation).orElseThrow().toString();
    }


    protected String reservationNumberGenerator(){

        UUID uuid = UUID.randomUUID();

        return uuid.toString().substring(24);

    }
}
