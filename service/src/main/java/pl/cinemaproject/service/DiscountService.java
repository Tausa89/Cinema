package pl.cinemaproject.service;

import pl.cinemaproject.persistence.enums.DiscountType;
import pl.cinemaproject.persistence.model.User;

import java.util.Objects;

public class DiscountService {





    public Integer getDiscount(int discountType){

        int discount;

        switch (discountType){

            case 1 -> discount = 10;
            case 2 -> discount = 15;
            case 3 -> discount = 20;
            default -> discount = 0;
        }

        return discount;
    }
    public Integer getDiscount(DiscountType discountType){

        int discount;

        switch (discountType){

            case STUDENT -> discount = 10;

            case SENIOR -> discount = 15;

            case CHILD -> discount = 30;

            default -> discount = 0;



        }

        return discount;


    }

    private Integer getDiscountForActiveUser(User user){

        if(Objects.nonNull(user)){
            return 10;
        }
        return 0;
    }


    public Integer countFinalPrice(Integer price, Integer discount){

        var discountAmount = (price*discount)/100;

        return price - discountAmount;

    }

}
