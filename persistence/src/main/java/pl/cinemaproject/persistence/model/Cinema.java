package pl.cinemaproject.persistence.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cinema {

    private Integer id;
    private String name;
    private Integer cityId;
}
