package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.enums.Status;
import pl.cinemaproject.persistence.model.SeancesSeat;
import pl.cinemaproject.persistence.model.view.SeancesView;
import pl.cinemaproject.service.CinemaService;
import pl.cinemaproject.service.UserService;
import pl.cinemaproject.ui.data.AdminUserDataService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserMenu {


    private final UserService userService;
    private final CinemaService cinemaService;


    public void getUserMainMenu() {


        while (true) {

            var option = userMainMenu();
            switch (option) {

                case 1 -> getSeancesWithSpecifiedCriteria();
                case 2 -> getReservationAndBuyTicketMenu();


                default -> System.out.println("Wrong input");

            }
        }

    }


    private void getSeancesWithSpecifiedCriteria() {


        var searchCriteria = getSearchCriteriaAsString();
        var listOfCriteria = userService.prepareListOfSearchCriteria(searchCriteria);

        var matchingSeances = userService.findSameWords(listOfCriteria);

        if (matchingSeances.isEmpty()) {
            System.out.println("There is no match for given criteria");
        } else
            matchingSeances.forEach(System.out::println);


    }


    private String getSearchCriteriaAsString() {


        System.out.println("""
                Please provide search criteria.
                You can use only letters nad numbers,
                evey new criteria need to be separate
                with comma from the last one for example:
                London,Avengers         or 
                London,21
                You can provide city name, cinema name,
                movie name or start time of the movie.
                """);

        return AdminUserDataService.getSearchCriteria("You can now provied criteria");
    }

    private int userMainMenu() {

        System.out.println("""
                Welcome to User Menu.
                                
                1. Pres 1 if you want to find available seance by any category.
                2. Pres 2 if you want buy or reserved a ticket.
                3. Pres 3 to exit user menu.
                """);

        return AdminUserDataService.getInt("Choose option");
    }

    public void getReservationAndBuyTicketMenu() {

        var option = reservationAndBuyTicketMenu();

        while (true) {

            switch (option) {

                case 1 -> buyOrReserveSeatFromAllSeances();


                case 2 -> buyOrReserveSeatForGivenCity();


                case 3 -> buyOrReserveForGivenCinema();


                case 4 -> buyOrReserveForGivenMovie();


                case 5 -> getSeancesWithSpecifiedCriteria();

                default -> System.out.println("Wrong input");


            }
        }


    }

    private void buyOrReserveForGivenMovie() {

        var seanceId = getChosenSeanceIdForSpecifiedMovieName();
        addNewSeanceSeat(seanceId);

    }

    private void addNewSeanceSeat(Integer seanceId) {
        cinemaService.printCinemaRoomView(seanceId);
        List<SeancesSeat> reservedSeats = new ArrayList<>();
        reserveOrBuyFirstSeanceSeat(seanceId, reservedSeats);
        if (orderProcessContinueMenu() == 2) {
            addNextSeatToSeatsList(seanceId, reservedSeats);
        }

        reservedSeats.forEach(System.out::println);
    }

    private void buyOrReserveForGivenCinema() {
        var seanceId = getChosenSeanceIdForSpecifiedCinemaName();
        addNewSeanceSeat(seanceId);

    }

    private void buyOrReserveSeatForGivenCity() {
        //ToDo musi reagowac jak nie znajdzie takiego miasta
        var seanceId = getChosenSeanceIdForSpecifiedCity();
        addNewSeanceSeat(seanceId);
    }


    private void buyOrReserveSeatFromAllSeances() {
        var seanceId = getChosenSeanceIdFromAllSeances();
        addNewSeanceSeat(seanceId);

    }



    private void reserveOrBuyFirstSeanceSeat(Integer seanceId, List<SeancesSeat> reservedSeats) {
        var buyOrReserve = getBuyOrReserveMenu();
        if (buyOrReserve == 1) {
            orderSeatAndAddToSeatList(seanceId, reservedSeats);

        } else if (buyOrReserve == 2) {
            reserveSeatAndAddToSeatList(seanceId, reservedSeats);
        } else {
            System.out.println("Wrong input");
        }
    }

    private void addNextSeatToSeatsList(Integer seanceId, List<SeancesSeat> reservedSeats) {
        var continueProcess = true;
        while (continueProcess) {
            cinemaService.checkIfRightSeatIsFree(reservedSeats.get(reservedSeats.size() - 1));
            cinemaService.checkIfLeftSeatIsFree(reservedSeats.get(reservedSeats.size() - 1));
            System.out.println("Which seat you want ?");
            if (reservedSeats.get(reservedSeats.size() - 1).getStatus() == Status.RESERVED) {
                reserveSeatAndAddToSeatList(seanceId, reservedSeats);
            } else {
                orderSeatAndAddToSeatList(seanceId, reservedSeats);
            }
            continueProcess = AdminUserDataService.getYesOrNo();

        }
    }

    private void reserveSeatAndAddToSeatList(Integer seanceId, List<SeancesSeat> reservedSeats) {
        reservedSeats.add(cinemaService.reserveSeat(seanceId,
                getRowNumber(),
                getPlaceNumber()));
    }

    private void orderSeatAndAddToSeatList(Integer seanceId, List<SeancesSeat> reservedSeats) {
        getChoseSeatInformation();
        reservedSeats.add(cinemaService.orderSeat(seanceId,
                getRowNumber(),
                getPlaceNumber()));
    }

    private Integer getChosenSeanceIdFromAllSeances() {

        var seances = userService.getAllSeances();
        return getSeanceId(seances);

    }



    private Integer getChosenSeanceIdForSpecifiedCity() {

        var cityName = AdminUserDataService.getString("Pleas provide city name");
        var seances = userService.getSeancesForSpecifiedCity(cityName);
        return getSeanceId(seances);

    }

    private Integer getChosenSeanceIdForSpecifiedCinemaName() {

        var cinemaName = AdminUserDataService.getString("Pleas provide cinema name");
        var seances = userService.getSeancesForSpecifiedCinema(cinemaName);
        return getSeanceId(seances);

    }


    private Integer getChosenSeanceIdForSpecifiedMovieName() {

        var movieName = AdminUserDataService.getString("Pleas provide movie name");
        var seances = userService.getSeancesForSpecifiedMovie(movieName);
        return getSeanceId(seances);

    }

    private Integer getSeanceId(List<SeancesView> seances) {
        userService.prepareSeancesListWithNumbers(seances).forEach(System.out::println);
        var number = AdminUserDataService.getInt("Chose seance by choosing seance number");
        return seances.get(number - 1).getId();
    }



    private int getPlaceNumber() {
        return AdminUserDataService.getInt("Provide place number");
    }

    private int getRowNumber() {
        return AdminUserDataService.getInt("Provide row number");
    }

    private int orderProcessContinueMenu() {

        System.out.println("""
                1. That's would be all?? Then press 1.
                2. If you want to continue processing Your order please press 2. 
                """);

        return AdminUserDataService.getInt("Chose option");
    }

    private int reservationAndBuyTicketMenu() {

        System.out.println("""
                You need to chose a seance for which one you want to buy or reserve a ticket
                1. If you want to chose from all available seances press 1.
                2. If you want to get seances for specified city press 2.
                3. If you want to get seances for specified cinema press 3.
                4. If you want to get seances for specified movie press 4.
                5. If you want to return to previous menu press 5.
                """);

        return AdminUserDataService.getInt("Chose option");
    }

    private int getBuyOrReserveMenu() {

        System.out.println("""
                                
                1.If you want buy a ticket pleas chose 1.
                2.If you want reserve a ticket pleas chose 2.
                """);

        return AdminUserDataService.getInt("Chose option");
    }



    private void getChoseSeatInformation() {

        System.out.println("""
                                
                Pleas chose a seat be given row number
                starting from top to bottom counting from 1
                then place number starting from left to right
                counting from 1, pleas use only numbers
                """);
    }


}
