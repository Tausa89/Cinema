package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.service.UserService;
import pl.cinemaproject.ui.data.AdminUserDataService;

@RequiredArgsConstructor
public class LoginMenu {
    private final UserService userService;

    public boolean logIn(){
        System.out.println("""
                1.If you are our active user please press 1 and then log in.
                2.If you don't have active account pleas press 2 and continue.
                """);

        var option = AdminUserDataService.getInt("Chose option");

        return (option == 1) && userService.userLogin(AdminUserDataService.getString("Provide Name"),
                AdminUserDataService.getString("Provide Password")) != null;
    }







}
