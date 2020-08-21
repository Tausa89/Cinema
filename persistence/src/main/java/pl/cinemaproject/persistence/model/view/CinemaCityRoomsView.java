package pl.cinemaproject.persistence.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.persistence.model.City;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CinemaCityRoomsView {

    private Cinema cinema;
    private City city;
    private List<CinemaRoom> cinemaRooms;
}
