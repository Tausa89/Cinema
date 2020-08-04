package pl.cinemaproject.persistence.modeldto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CinemaRoomDTO {



    private String name;
    private int rows;
    private int seats;

}
