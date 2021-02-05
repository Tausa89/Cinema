package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.ui.data.AdminUserDataService;

@RequiredArgsConstructor
public class MainMenu {


    private final AdminMenu adminMenu;
    private final  CreateUserMenu createUserMenu;
    private final UserMenu userMenu;






    public void getMainMenu(){


        while (true){

            var option = mainMenu();

            switch (option){

                case 1 -> userMenu.getSeancesWithSpecifiedCriteria();
                case 2 -> userMenu.getReservationAndBuyTicketMenu();
                case 3 -> createUserMenu.getUserAccountMenu();
                case 4 -> adminMenu.getAdminMainMenu();
                case 5 -> {
                    System.out.println("Have a good day");
                    return;
                }
                default -> System.out.println("Wrong input");
            }


        }


    }


    private int mainMenu(){

        System.out.println("""
                
                Welcome to our main menu.
                
                1.If you want to just check available seances please press 1.
                2.If you want to reserve a seat or buy a ticket pleas press 2.
                3.If you want to create new acount or manage current one press 3.
                4.If you are admin and you want to log in press 4.
                5.If you want to exit press 5.
                
                """);


        return AdminUserDataService.getInt("Chose option");

    }

}
