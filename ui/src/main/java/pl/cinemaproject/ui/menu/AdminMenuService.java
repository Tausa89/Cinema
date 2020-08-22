package pl.cinemaproject.ui.menu;

import pl.cinemaproject.ui.data.AdminDataService;

public class AdminMenuService {



    public void adminMainMenu(){


        while (true){
            try{

                var option = choseOption();
                switch (option){
                    case 1 -> optionOne();
                    case 2 -> optionTwo();
                    case 3 -> optionThree();
                    case 4 -> {
                        System.out.println("Have a good day");
                        return;
                    }
                    default -> System.out.println("Wrong input");

                }


            }catch (Exception e){
                System.out.println("Exception");
                System.out.println(e.getMessage());
            }
        }

    }

    private int optionThree() {

        System.out.println("1. Do you want to remove city?");
        System.out.println("2. Do you want to remove cinema?");
        System.out.println("3. Do you want to remove cinema room?");

        return AdminDataService.getInt("Chose option");
    }

    private int optionTwo() {

        System.out.println("1. Do you want update city?");
        System.out.println("2. Do you want to update cinema?");
        System.out.println("3. Do you want to update cinema room?");
        System.out.println("4. Do you want to update cinema room seats?");

        return AdminDataService.getInt("Chose option");
    }

    private int optionOne() {

        System.out.println("1. Do you want add new City?");
        System.out.println("2. Do you want to add new cinema?");
        System.out.println("3. Do you want to add new cinema room?");

        return AdminDataService.getInt("Chose option");
    }

    private int choseOption() {

        System.out.println("Welcome to admin panel, what you want to do?");
        System.out.println("1. Do you want add some new data?");
        System.out.println("2. Do you want to update some current data?");
        System.out.println("3. Do you want to remove some current data");
        System.out.println("4. Close menu.");
        System.out.println("5.");
        System.out.println("6.");

        return AdminDataService.getInt("Chose option");

    }

}
