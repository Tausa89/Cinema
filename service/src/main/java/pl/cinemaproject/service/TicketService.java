package pl.cinemaproject.service;


import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.enums.DiscountType;
import pl.cinemaproject.persistence.model.SeancesSeat;
import pl.cinemaproject.persistence.model.Ticket;
import pl.cinemaproject.persistence.model.User;


import java.math.BigDecimal;

@RequiredArgsConstructor
public class TicketService {


    private final DiscountService discountService;
    private final BigDecimal TICKET_PRICE = BigDecimal.valueOf(20);

    public Ticket generateTicket(User user,SeancesSeat seancesSeat, DiscountType discountType){

        var discount = discountService.countDiscount(user,discountType);
        var finalPrice = discountService.countFinalPrice(TICKET_PRICE.intValue(),discount);
        return Ticket
                .builder()
                .discount(discount)
                .finalePrice(BigDecimal.valueOf(finalPrice))
                .seanceId(seancesSeat.getSeanceId())
                .seatId(seancesSeat.getSeatId())
                .price(TICKET_PRICE)
                .userId(user.getId())
                .build();
    }

}
