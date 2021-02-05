package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.enums.DiscountType;
import pl.cinemaproject.service.DiscountService;
import pl.cinemaproject.ui.data.AdminUserDataService;

@RequiredArgsConstructor
public class DiscountMenu {


    private final DiscountService discountService;



//    public DiscountType getDiscountMenu(){
//
//        var option = discountMenu();
//        switch (option){
//
//            case 1 -> {return DiscountType.STUDENT;}
//
//            case 2 -> {return  DiscountType.CHILD;}
//
//            case 3 -> {return DiscountType.SENIOR;}
//
//            default -> {return DiscountType.NORMAL;}
//        }
//
//    }




    public int discountMenu(){

        System.out.println("""
                Chose Your discount
                1. Student discount pleas press 1.
                2. If you are under 12 years old press 2 for kid's discount.
                3. If you are older than 60 press 3 to get Senior discount.
                4. Press 4 if you are not allowed to get any discount
                
                Remember that you will be obligate to show required document
                enabling to get specified discount  
                """);


        return AdminUserDataService.getInt("Chose option");

    }
}
