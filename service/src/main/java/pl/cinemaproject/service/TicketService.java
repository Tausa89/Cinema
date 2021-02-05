package pl.cinemaproject.service;


import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.enums.DiscountType;
import pl.cinemaproject.persistence.model.SeancesSeat;
import pl.cinemaproject.persistence.model.Ticket;
import pl.cinemaproject.persistence.model.User;
import pl.cinemaproject.repository.TicketRepository;


import java.math.BigDecimal;

@RequiredArgsConstructor
public class TicketService {


    private final TicketRepository ticketRepository;
    private final DiscountService discountService;
    private final BigDecimal TICKET_PRICE = BigDecimal.valueOf(20);

    public Ticket generateTicket(SeancesSeat seancesSeat, int discountType){

        var discount = discountService.getDiscount(discountType);
        var finalPrice = discountService.countFinalPrice(TICKET_PRICE.intValue(),discount);
        return Ticket
                .builder()
                .discount(discount)
                .finalePrice(BigDecimal.valueOf(finalPrice))
                .seanceId(seancesSeat.getSeanceId())
                .seatId(seancesSeat.getSeatId())
                .price(TICKET_PRICE)
                .build();
    }


    public Ticket generateTicketForActiveUser(SeancesSeat seancesSeat, int discountType){

        var discount = discountService.getDiscount(discountType) + 10;
        var finalPrice = discountService.countFinalPrice(TICKET_PRICE.intValue(),discount);
        return Ticket
                .builder()
                .discount(discount)
                .finalePrice(BigDecimal.valueOf(finalPrice))
                .seanceId(seancesSeat.getSeanceId())
                .seatId(seancesSeat.getSeatId())
                .price(TICKET_PRICE)
                .build();
    }


    public String addTicket(Ticket ticket){

        return ticketRepository.add(ticket).orElseThrow().toString();
    }

}
