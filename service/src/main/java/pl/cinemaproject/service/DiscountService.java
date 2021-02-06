package pl.cinemaproject.service;

import lombok.NonNull;
public class DiscountService {





    public Integer getDiscount(@NonNull Integer discountType){

        int discount;

        switch (discountType){

            case 1 -> discount = 10;
            case 2 -> discount = 15;
            case 3 -> discount = 20;
            default -> discount = 0;
        }

        return discount;
    }




    public Integer countFinalPrice(@NonNull Integer price,@NonNull Integer discount){

        var discountAmount = (price*discount)/100;

        return price - discountAmount;

    }

}
