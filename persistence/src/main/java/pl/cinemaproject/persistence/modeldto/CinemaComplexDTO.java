package pl.cinemaproject.persistence.modeldto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CinemaComplexDTO {

    private String cinemaName;
    private String cityName;
    private List<CinemaRoomDTO> cinemaRooms;
}
