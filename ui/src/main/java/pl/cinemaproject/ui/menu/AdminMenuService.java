package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.service.AdminService;
import pl.cinemaproject.ui.data.AdminDataService;

@RequiredArgsConstructor
public class AdminMenuService {


    private final AdminService adminService;

    public void getAdminMainMenu() {

        while (true) {
            try {

                var option = adminMainMenu();
                switch (option) {
                    case 1 -> getAddNewDataMenu();
                    case 2 -> getUpdateDataMenu();
                    case 3 -> getRemoveDataMenu();
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

    private void getAddNewDataMenu() {

        while (true) {
            try {

                var option = addNewDataMenu();
                switch (option) {
                    case 1 -> addNewCity();
                    case 2 -> getAddNewCinemaMenu();
                    case 3 -> addNewCinemaRoom();
                    case 4 -> getAdminMainMenu();
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

    private void addNewCity() {

        var cityName = AdminDataService.getString("Pleas provide city name");
        var addedCity = adminService.addNewCity(cityName);
        System.out.println(addedCity + " was successfully added to database");
    }

    private void getAddNewCinemaMenu() {

        while (true) {
            try {

                var option = addNewCinemaMenu();
                switch (option) {
                    case 1 -> addNewCinemaToExistingCity();
                    case 2 -> addNewCinemaWithNewCity();
                    case 3 -> {
                        getAddNewDataMenu();
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

    private void addNewCinemaToExistingCity() {

        var cityName = AdminDataService.getString("Pleas provide city name");
        var cityId = adminService.findCityIdByName(cityName);
        var newCinema = Cinema
                .builder()
                .name(AdminDataService.getString("Provide cinema name"))
                .cityId(cityId)
                .build();

        System.out.println("Successfully added cinema " + adminService.addNewCinema(newCinema));

    }

    private int addNewCinemaMenu() {
        System.out.println(
                """
                        1. You want add cinema to existing city?
                        2. You want add cinema to no existing city, then first you need to add city.
                        3. Return to previous menu.
                        4. Exit menu.
                        """);

        return AdminDataService.getInt("Chose option");
    }

    private void addNewCinemaRoom() {

        System.out.println("""
                You can add cinema room only to existing cinema,
                to which cinema you want to add new cinema room
                """);
        var cinemaId = adminService.findCinemaIdByName(AdminDataService.getString("Provide cinema name"));
        CinemaRoom newCinemaRoom = CinemaRoom
                .builder()
                .cinemaId(cinemaId)
                .name(AdminDataService.getString("Provide cinema room name"))
                .places(AdminDataService.getInt("Provide number of places"))
                .rowsNumber(AdminDataService.getInt("Provide number of rows"))
                .build();

        System.out.println("Cinema room " + adminService.addNewCinemaRoom(newCinemaRoom) + " was successfully added");

        //ToDo generate Seats

    }

    private void getUpdateDataMenu() {

        while (true) {
            var option = updateDataMenu();
            switch (option) {
                case 1 -> updateCity();
//                case 2 -> updateCinema();
//                case 3 -> updateCinemaRoom();
//                case 4 -> updateCinemaRoomSeats();
                case 5 -> {
                    updateDataMenu();
                    return;
                }
            }
        }
    }

    private int updateDataMenu() {

        System.out.println(
                """
                        1. Do you want update city?
                        2. Do you want to update cinema?
                        3. Do you want to update cinema room?
                        4. Do you want to update cinema room seats?
                        5. Return to previous menu;
                        """);

        return AdminDataService.getInt("Chose option");
    }


    private void updateCity() {


        var cityToUpdate = AdminDataService.getString("Pleas provide name of existing city");
        var city = adminService.findCityIdByName(cityToUpdate);
        var updatedCity = City.builder().name("One").id(city).build();

        adminService.updateCity(updatedCity);


    }

    private void getRemoveDataMenu() {


        while (true) {
            var option = removeDataMenu();
            switch (option) {
                case 1 -> removeCity();
//                case 2 -> updateCinema();
//                case 3 -> updateCinemaRoom();
//                case 4 -> updateCinemaRoomSeats();
                case 5 -> {
                    updateDataMenu();
                    return;
                }
            }
        }
    }


    private int removeDataMenu() {

        System.out.println(
                """
                        1. Do you want to remove city?
                        2. Do you want to remove cinema?
                        3. Do you want to remove cinema room?
                        """);

        return AdminDataService.getInt("Chose option");
    }

    private String removeCity(){

        var cityToRemove = AdminDataService.getString("City name");

        return adminService.removeCity(cityToRemove);

    }


    private int addNewDataMenu() {

        System.out.println(
                """
                        1. Do you want add new City ?
                        2. Do you want to add new cinema?
                        3. Do you want to add new cinema room?
                        4. Return to previous menu
                        """);

        return AdminDataService.getInt("Chose option");
    }

    private int adminMainMenu() {

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
