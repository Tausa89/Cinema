package pl.cinemaproject.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CinemaComplex {

    private Cinema cinema;
    private City city;
    private List<CinemaRoom> cinemaRooms;
}
