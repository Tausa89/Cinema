package pl.cinemaproject.service;

import pl.cinemaproject.repository.UserRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;
import pl.cinemaproject.repository.impl.UserRepositoryImpl;

class UserMenuServiceTest {



    final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String USERNAME = "hbstudent";
    final String PASSWORD = "hbstudent";


    DatabaseConnector connection = new DatabaseConnector(URL, USERNAME, PASSWORD);
    UserRepository userRepository = new UserRepositoryImpl(connection);
    UserService userService = new UserService(userRepository);

//    @Test
//    void shouldHashPassword(){
//
//        var hashedPassword= userMenuService.passwordHashing("Asd");
//
//        System.out.println(hashedPassword);
//
//        assertNotNull(hashedPassword);
//    }

//    @Test
//    void shouldReturnUser(){
//
//
//        User user = userMenuService.userLogin("Zenek007", "Typ1234!");
//
//        assertNotNull(user);
//
//    }

}