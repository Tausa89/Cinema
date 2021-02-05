package pl.cinemaproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.cinemaproject.persistence.enums.DiscountType;
import pl.cinemaproject.persistence.model.User;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;

    @Test
    void shouldReturnCorrectDiscount(){




        var discount = discountService.getDiscount(1);
        var discountTwo = discountService.getDiscount(2);
        var discountThree = discountService.getDiscount(3);


        assertEquals(10, discount);
        assertEquals(15, discountTwo);
        assertEquals(20, discountThree);

    }


    @Test
    void shouldReturnFinalPrice(){

        var finalPrice = discountService.countFinalPrice(20,30);
        var finalPriceTwo = discountService.countFinalPrice(20,10);
        var finalPriceThere = discountService.countFinalPrice(20,0);

        assertEquals(14,finalPrice);
        assertEquals(18,finalPriceTwo);
        assertEquals(20,finalPriceThere);
    }

}