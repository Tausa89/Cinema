package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.service.AdminService;
import pl.cinemaproject.ui.data.AdminDataService;

@RequiredArgsConstructor
public class AdminMenuService {


    private final AdminService adminService;


    public void adminMainMenu() {


        while (true) {
            try {

                var option = choseOption();
                switch (option) {
                    case 1 -> addNewDataMenu();
                    case 2 -> optionTwo();
                    case 3 -> optionThree();
                    case 4 -> {
                        System.out.println("Have a good day");
                        return;
                    }
                    default -> System.out.println("Wrong input");

                }


            } catch (Exception e) {
                System.out.println("Exception");
                System.out.println(e.getMessage());
            }
        }

    }


    private void addNewDataMenu() {


        while (true) {
            try {

                var option = optionOne();
                switch (option) {
                    case 1 -> addNewCity();
                    case 2 -> addNewCinemaMenu();
                    case 3 -> addNewCinemaRoom();
                    case 4 -> adminMainMenu();
                    case 5 -> {
                        System.out.println("Have a good day");
                        return;
                    }
                    default -> System.out.println("Wrong input");
                }
            } catch (Exception e) {
                System.out.println("Exception");
                System.out.println(e.getMessage());
            }
        }

    }

    private void addNewCinemaRoom() {

        System.out.println("To which cinema you want to add new cinema room");

    }

    private void addNewCinemaMenu() {

        while (true) {
            try {

                var option = addNewCinemaMenuInfo();
                switch (option) {
                    case 1 -> addNewCinemaToExistingCity();
                    case 2 -> addNewCinemaWithNewCity();
                    case 3 -> {
                        addNewDataMenu();
                        return;
                    }
                    default -> System.out.println("Wrong input");
                }
            } catch (Exception e) {
                System.out.println("Exception");
                System.out.println(e.getMessage());
            }
        }

    }

    private void addNewCinemaWithNewCity() {

        System.out.println("You need to add city first");
        addNewCity();
        System.out.println("Repeat city name to which one you want add cinema");
        addNewCinemaToExistingCity();
    }

    private void addNewCinemaToExistingCity(){

        var cityName = AdminDataService.getString("Pleas provide city name");
        var cityId = adminService.findCityIdByName(cityName);
        var newCinema = Cinema
                .builder()
                .name(AdminDataService.getString("Provide cinema name"))
                .cityId(cityId)
                .build();

        System.out.println("Successfully added cinema " + adminService.addNewCinema(newCinema));

    }


    private int addNewCinemaMenuInfo() {
        System.out.println(
                """
                        1. You want add cinema to existing city?
                        2. You want add cinema to no existing city, then first you need to add city.
                        3. Return to previous menu.
                        4. Exit menu.
                        """);

        return AdminDataService.getInt("Chose option");
    }

    private void addNewCity() {

        var cityName = AdminDataService.getString("Pleas provide city name");
        var addedCity = adminService.addNewCity(cityName);
        System.out.println(addedCity + " was successfully added to database");
    }

    private int optionThree() {

        System.out.println(
                """
                        1. Do you want to remove city?
                        2. Do you want to remove cinema?
                        3. Do you want to remove cinema room?
                        """);

        return AdminDataService.getInt("Chose option");
    }

    private int optionTwo() {

        System.out.println(
                """
                        1. Do you want update city?
                        2. Do you want to update cinema?
                        3. Do you want to update cinema room?
                        4. Do you want to update cinema room seats?
                        """);

        return AdminDataService.getInt("Chose option");
    }

    private int optionOne() {

        System.out.println(
                """
                        1. Do you want add new City?
                        2. Do you want to add new cinema?
                        3. Do you want to add new cinema room?
                        4. Return to previous menu
                        """);

        return AdminDataService.getInt("Chose option");
    }

    private int choseOption() {

        System.out.println(
                """
                        Welcome to admin panel, what you want to do?
                        1. Do you want add some new data?
                        2. Do you want to update some current data?
                        3. Do you want to remove some current data
                        4. Close menu.
                        """);


        return AdminDataService.getInt("Chose option");

    }

}
