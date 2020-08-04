package pl.cinemaproject.persistence.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Ticket {

    private Integer id;
    private Integer seanceId;
    private Integer seatId;
    private BigDecimal price;
    private int discount;
    private Integer userId;
}
