package pl.cinemaproject.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cinemaproject.persistence.enums.Status;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SeancesSeat {

    private Integer id;
    private Integer seatId;
    private Integer seanceId;
    private Status status;
}
