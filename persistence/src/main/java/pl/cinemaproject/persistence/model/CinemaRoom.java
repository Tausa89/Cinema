package pl.cinemaproject.persistence.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CinemaRoom {

    private Integer id;
    private String name;
    private int rows;
    private int seats;
    private Integer cinemaId;
}
