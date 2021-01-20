package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.User;
import pl.cinemaproject.service.UserMenuService;
import pl.cinemaproject.ui.data.AdminUserDataService;

@RequiredArgsConstructor
public class CreateUserMenu {


    private final UserMenuService userMenuService;




    public void getUserAccountMenu(){

        while (true){

            var option = userAccountMenu();

            switch (option){

                case 1 -> addNewUser();
                case 2 -> deleteUser();
                case 3 -> System.out.println("current not available");
                case 4 -> System.out.println("current not available");
                case 5 -> {
                    System.out.println("Return to previous menu");
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }




    }


    private int userAccountMenu(){

        System.out.println("""
                Welcome to account manager.
                
                1.If you want to create new account press 1.
                2.If you want to delete account press 2.
                3.If you want to change username press 3.
                4.If you want to change password press 4.
                5.If you want to return to main menu press 5.
                """);


        return AdminUserDataService.getInt("Chose option");


    }









    private void addNewUser(){

        var newUser = User
                .builder()
                .name(AdminUserDataService.getString("Pleas provide name"))
                .surname(AdminUserDataService.getString("Pleas provide surname"))
                .username(AdminUserDataService.getString("Pleas provide username"))
                .password(AdminUserDataService.checkPassword())
                .email(AdminUserDataService.getEmailAddress("Pleas provide email"))
                .build();

        System.out.println(userMenuService.addNewUser(newUser) + " was successful created");

    }


    private void deleteUser(){
        var user = userMenuService.findByUserName(AdminUserDataService.getString("Pleas provied username"));
        System.out.println(userMenuService.deleteUser(user, AdminUserDataService.getString("Pleas provied password")));

    }


}
