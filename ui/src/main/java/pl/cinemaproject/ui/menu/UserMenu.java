package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.enums.Status;
import pl.cinemaproject.persistence.model.SeancesSeat;
import pl.cinemaproject.persistence.model.view.SeancesView;
import pl.cinemaproject.service.CinemaService;
import pl.cinemaproject.service.ReservationService;
import pl.cinemaproject.service.SeancesService;
import pl.cinemaproject.service.TicketService;
import pl.cinemaproject.ui.data.AdminUserDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserMenu {


    private final SeancesService seancesService;
    private final CinemaService cinemaService;
    private final LoginMenu loginMenu;
    private final TicketService ticketService;
    private final DiscountMenu discountMenu;
    private final ReservationService reservationService;


    public void getSeancesWithSpecifiedCriteria() {

        //merge this two to sth like getMatchingSeances(string searchCriteria) in seancesService
        var listOfCriteria = seancesService.prepareListOfSearchCriteria(getSearchCriteriaAsString());
        //It doesn't tell what it's doing
        var matchingSeances = seancesService.findSameWords(listOfCriteria);

        if (matchingSeances.isEmpty()) {
            System.out.println("There is no match for given criteria");
        } else {
            matchingSeances.forEach(System.out::println);
        }

    }


    private String getSearchCriteriaAsString() {

        System.out.println("""
                Please provide search criteria.
                You can use only letters nad numbers,
                evey new criteria need to be separate
                with comma from the last one for example:
                London,Avengers         or 
                London,21
                You can provide city name, ci4nema name,
                movie name or start time of the movie.
                """);

        //AdminUserDataService? Standard user shouldn't use that?
        return AdminUserDataService.getSearchCriteria("You can now provied criteria");
    }

    public void getReservationAndBuyTicketMenu() {

        var option = reservationAndBuyTicketMenu();

        while (true) {
            switch (option) {
                case 1 -> checkIfUserWantBuyOrReserveTicket(buyOrReserveSeatFromAllSeances());
                case 2 -> checkIfUserWantBuyOrReserveTicket(buyOrReserveSeatForGivenCity());
                case 3 -> checkIfUserWantBuyOrReserveTicket(buyOrReserveForGivenCinema());
                case 4 -> checkIfUserWantBuyOrReserveTicket(buyOrReserveForGivenMovie());
                case 5 -> getSeancesWithSpecifiedCriteria();
                default -> System.out.println("Wrong input");
            }
        }


    }

    //if function is named "check..." then it can't make any changes anywhere, it can only return bool/exception
    private void checkIfUserWantBuyOrReserveTicket(List<SeancesSeat> seats) {
        if (checkStatus(seats)) {
            saveAndGenerateReservation(seats);
        } else {
            checkForActiveUser(seats);
        }
    }

    //same as above, remove editing logic or rename function to be more meaningful
    private void checkForActiveUser(List<SeancesSeat> seats) {
        if (checkIfUserHaveAccount()) {
            saveAndGenerateTicketsForActiveUser(seats);
        } else {
            saveAndGenerateTicketForInactiveUser(seats);
        }
    }

    private void saveAndGenerateTicketForInactiveUser(List<SeancesSeat> seats) {
        seats.forEach(cinemaService::addSeanceSeat);
        seats.stream().map(p -> generateTicketForUnActiveUser(p, discountMenu.discountMenu()))
                .collect(Collectors.toList()).forEach(System.out::println);
    }

    private void saveAndGenerateTicketsForActiveUser(List<SeancesSeat> seats) {
        seats.forEach(cinemaService::addSeanceSeat);
        seats.stream().map(p -> generateTicketForActiveUser(p, discountMenu.discountMenu()))
                .collect(Collectors.toList()).forEach(System.out::println);
    }

    private void saveAndGenerateReservation(List<SeancesSeat> seats) {
        seats.forEach(cinemaService::addSeanceSeat);
        seats.stream().map(this::generateReservation).collect(Collectors.toList()).forEach(System.out::println);
    }

    private List<SeancesSeat> buyOrReserveForGivenMovie() {

        var seanceId = getChosenSeanceIdForSpecifiedMovieName();
        return addNewSeanceSeat(seanceId);
    }

    private List<SeancesSeat> addNewSeanceSeat(Integer seanceId) {
        cinemaService.printCinemaRoomView(seanceId);
        List<SeancesSeat> reservedSeats = new ArrayList<>();
        reserveOrBuyFirstSeanceSeat(seanceId, reservedSeats);

        if (orderProcessContinueMenu() == 2) {
            addNextSeatToSeatsList(seanceId, reservedSeats);
        }

        reservedSeats.forEach(System.out::println);
        return reservedSeats;
    }

    private List<SeancesSeat> buyOrReserveForGivenCinema() {
        var seanceId = getChosenSeanceIdForSpecifiedCinemaName();
        return addNewSeanceSeat(seanceId);

    }

    private List<SeancesSeat> buyOrReserveSeatForGivenCity() {
        //ToDo musi reagowac jak nie znajdzie takiego miasta
        //then just add throwing custom exception - the worst thing can happen is error :D
        var seanceId = getChosenSeanceIdForSpecifiedCity();
        return addNewSeanceSeat(seanceId);
    }

    //not single responsibility
    private List<SeancesSeat> buyOrReserveSeatFromAllSeances() {
        var seanceId = getChosenSeanceIdFromAllSeances();
        return addNewSeanceSeat(seanceId);
    }

    //not single responsibility
    private void reserveOrBuyFirstSeanceSeat(Integer seanceId, List<SeancesSeat> reservedSeats) {
        var buyOrReserve = getBuyOrReserveMenu();
        if (buyOrReserve == 1) {
            addOrderedSeatToSeatList(seanceId, reservedSeats);

        } else if (buyOrReserve == 2) {
            addReservedSeatToSeatList(seanceId, reservedSeats);
        } else {
            System.out.println("Wrong input");
        }
    }

    private void addNextSeatToSeatsList(Integer seanceId, List<SeancesSeat> reservedSeats) {
        var continueProcess = true;
        while (continueProcess) {
            //what this checkFunctions are doing? they tells that they are checking, and they don't return anything,
            //so they are changing sth, so they are wrongly named
            cinemaService.checkIfRightSeatIsFree(reservedSeats.get(reservedSeats.size() - 1));
            cinemaService.checkIfLeftSeatIsFree(reservedSeats.get(reservedSeats.size() - 1));
            System.out.println("Which seat you want ?");
            if (reservedSeats.get(reservedSeats.size() - 1).getStatus() == Status.RESERVED) {
                addReservedSeatToSeatList(seanceId, reservedSeats);
            } else {
                addOrderedSeatToSeatList(seanceId, reservedSeats);
            }
            continueProcess = AdminUserDataService.getYesOrNo("Do you want to continue? If yes Press Y if not press N");

        }
    }

    private void addReservedSeatToSeatList(Integer seanceId, List<SeancesSeat> reservedSeats) {
        getChoseSeatInformation();
        reservedSeats.add(cinemaService.prepareSeatForReservation(seanceId,
                getRowNumber(),
                getPlaceNumber()));
    }


    private void addOrderedSeatToSeatList(Integer seanceId, List<SeancesSeat> reservedSeats) {
        getChoseSeatInformation();
        reservedSeats.add(cinemaService.prepareSeatForOrder(seanceId,
                getRowNumber(),
                getPlaceNumber()));
    }

    private Integer getChosenSeanceIdFromAllSeances() {

        var seances = seancesService.getAllSeances();
        return getSeanceId(seances);

    }

    private Integer getChosenSeanceIdForSpecifiedCity() {

        var cityName = AdminUserDataService.getString("Pleas provide city name");
        var seances = seancesService.getSeancesForSpecifiedCity(cityName);
        return getSeanceId(seances);

    }

    private Integer getChosenSeanceIdForSpecifiedCinemaName() {

        var cinemaName = AdminUserDataService.getString("Pleas provide cinema name");
        var seances = seancesService.getSeancesForSpecifiedCinema(cinemaName);
        return getSeanceId(seances);

    }


    private Integer getChosenSeanceIdForSpecifiedMovieName() {

        var movieName = AdminUserDataService.getString("Pleas provide movie name");
        var seances = seancesService.getSeancesForSpecifiedMovie(movieName);
        return getSeanceId(seances);

    }


    //getSeanceIdFromUser, getSeanceId sounds like simple Getter for seance object
    private Integer getSeanceId(List<SeancesView> seances) {
        seancesService.prepareSeancesListWithNumbers(seances).forEach(System.out::println);
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
        //Admin user? Sounds like it should be in sth like ConsoleUIHelper or sth like this
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

    private boolean checkStatus(List<SeancesSeat> seats) {
        return seats.get(0).getStatus() == Status.RESERVED;
    }

    //ToDo ogarnąć żeby wyświetlało to co trzeba w ToString
    private String generateReservation(SeancesSeat seancesSeat) {
        return reservationService.addReservation(reservationService.generateReservation(seancesSeat));
    }


    private boolean checkIfUserHaveAccount() {
        return loginMenu.logIn();
    }

    //generate sounds like it should return ticket, it should be named like "addTicket..."
    private String generateTicketForActiveUser(SeancesSeat seancesSeat, int discount) {
        return ticketService.addTicket(ticketService.generateTicketForActiveUser(seancesSeat, discount));
    }

    private String generateTicketForUnActiveUser(SeancesSeat seancesSeat, int discount) {
        return ticketService.addTicket(ticketService.generateTicket(seancesSeat, discount));
    }
}
