package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.Cinema;
import pl.cinemaproject.persistence.model.CinemaRoom;
import pl.cinemaproject.persistence.model.City;
import pl.cinemaproject.service.AdminService;
import pl.cinemaproject.ui.data.AdminUserDataService;

@RequiredArgsConstructor
public class AdminMenu {


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

        var cityName = AdminUserDataService.getString("Pleas provide city name");
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

        var cityName = AdminUserDataService.getString("Pleas provide city name");
        var cityId = adminService.findCityIdByName(cityName);
        var newCinema = Cinema
                .builder()
                .name(AdminUserDataService.getString("Provide cinema name"))
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

        return AdminUserDataService.getInt("Chose option");
    }

    private void addNewCinemaRoom() {

        System.out.println("""
                You can add cinema room only to existing cinema,
                to which cinema you want to add new cinema room
                """);
        printAllCinemasNames();

        var cinemaId = adminService.findCinemaIdByName(AdminUserDataService.getString("Provide cinema name"));
        CinemaRoom newCinemaRoom = CinemaRoom
                .builder()
                .cinemaId(cinemaId)
                .name(AdminUserDataService.getString("Provide cinema room name"))
                .places(AdminUserDataService.getInt("Provide number of places"))
                .rowsNumber(AdminUserDataService.getInt("Provide number of rows"))
                .build();

        System.out.println("Cinema room " + adminService.addNewCinemaRoom(newCinemaRoom) + " was successfully added");



    }

    private void getUpdateDataMenu() {

        while (true) {
            var option = updateDataMenu();
            switch (option) {
                case 1 -> updateCity();
                case 2 -> updateCinema();
                case 3 -> updateCinemaRoom();
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

        return AdminUserDataService.getInt("Chose option");
    }


    private void updateCity() {


        printAllCitiesNames();
        var cityToUpdate = AdminUserDataService.getString("Pleas provide name of existing city");
        var city = adminService.findCityIdByName(cityToUpdate);
        var updatedCity = City
                .builder()
                .name(AdminUserDataService.getString("Provide new city name"))
                .id(city)
                .build();

        System.out.println(adminService.updateCity(updatedCity));


    }



    private void updateCinema() {
        printAllCinemasNames();
        var cinemaToUpdate = adminService.getCinemaByName(AdminUserDataService.getString("Provide name of existing cinema"));

        cinemaToUpdate.setName(AdminUserDataService.getString("Provide new cinema name"));

        System.out.println(adminService.updateCinema(cinemaToUpdate));
    }


    private void updateCinemaRoom() {

        printAllCinemasNames();
        var cinemaId = adminService.getCinemaByName(AdminUserDataService.getString("For which cinema you want to update cinema room?")).getId();
        adminService.getAllCinemaRoomsForOneCinema(cinemaId).forEach(System.out::println);
        var cinemaRoomToUpdate = adminService.getCinemaRoomByName(AdminUserDataService.getString("Provide cinema room name"));

        cinemaRoomToUpdate.setName(AdminUserDataService.getString("Provide new cinema room name"));
        System.out.println(adminService.updateCinemaRoom(cinemaRoomToUpdate));



    }



    private void getRemoveDataMenu() {


        while (true) {
            var option = removeDataMenu();
            switch (option) {
                case 1 -> removeCity();
                case 2 -> removeCinema();
                case 3 -> removeCinemaRoom();
                case 4 -> {
                    removeDataMenu();
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
                        4. Return to previous menu.
                        """);

        return AdminUserDataService.getInt("Chose option");
    }

    private void removeCity(){

        printAllCitiesNames();
        var cityToRemove = AdminUserDataService.getString("""
                Before remove city you need to be aware 
                that all connected data with this city like cinemas, cinema rooms etc
                will also be deleted.
                
                Pleas provide name of city you want to remove. 
                """);
        System.out.println(adminService.removeCity(cityToRemove));

    }

    private void printAllCitiesNames() {
        adminService.getAllCitiesNames().forEach(System.out::println);
    }


    private void removeCinema(){

        printAllCinemasNames();
        var cinemaToRemove = AdminUserDataService.getString("""
                Before remove the cinema you need to be aware 
                that all connected data like cinema rooms and seats
                will also be deleted.
                
                Pleas provide name of cinema you want to remove. 
                """);

        System.out.println(adminService.removeCinema(cinemaToRemove));
    }

    private void  removeCinemaRoom(){
        printAllCinemasNames();
        var cinemaName = AdminUserDataService.getString("""
                Please provide the name of a cinema in 
                which one you want to remove cinema room. 
                """);

        var cinemaId = adminService.findCinemaIdByName(cinemaName);
        adminService.getAllCinemaRoomsForOneCinema(cinemaId).forEach(System.out::println);

        var cinemaRoomToRemove = AdminUserDataService.getString("""
                Before remove the cinema room you need to be aware 
                that all connected data like cinema seats
                will also be deleted.
                
                Pleas provide the name of a cinema room you want to remove. 
                """);

        System.out.println(adminService.removeCinemaRoom(cinemaRoomToRemove,cinemaName));



    }


    private int addNewDataMenu() {

        System.out.println(
                """
                        1. Do you want add new City ?
                        2. Do you want to add new cinema?
                        3. Do you want to add new cinema room?
                        4. Return to previous menu
                        """);

        return AdminUserDataService.getInt("Chose option");
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


        return AdminUserDataService.getInt("Chose option");

    }

    private void printAllCinemasNames() {
        adminService.getAllCinemasNames().forEach(System.out::println);
    }

}
