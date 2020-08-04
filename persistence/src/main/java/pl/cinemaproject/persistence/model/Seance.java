package pl.cinemaproject.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Seance {

    private Integer id;
    private Integer movieId;
    private Integer cinemaRoomId;
    private LocalDate startDateTime;
}
