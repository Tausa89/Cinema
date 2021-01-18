package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.User;
import pl.cinemaproject.service.UserMenuService;
import pl.cinemaproject.ui.data.AdminUserDataService;

@RequiredArgsConstructor
public class CreateUserMenu {


    private final UserMenuService userMenuService;





    public void addNewUser(){

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


    public void deleteUser(){
        var user = userMenuService.findByUserName(AdminUserDataService.getString("Pleas provied username"));
        System.out.println(userMenuService.deleteUser(user, AdminUserDataService.getString("Pleas provied password")));

    }


}
