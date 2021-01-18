package pl.cinemaproject.service;

import org.junit.jupiter.api.Test;
import pl.cinemaproject.repository.UserRepository;
import pl.cinemaproject.repository.generic.DatabaseConnector;
import pl.cinemaproject.repository.impl.UserRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;

class UserMenuServiceTest {



    final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String USERNAME = "hbstudent";
    final String PASSWORD = "hbstudent";


    DatabaseConnector connection = new DatabaseConnector(URL, USERNAME, PASSWORD);
    UserRepository userRepository = new UserRepositoryImpl(connection);
    UserMenuService userMenuService = new UserMenuService(userRepository);

//    @Test
//    void shouldHashPassword(){
//
//        var hashedPassword= userMenuService.passwordHashing("Asd");
//
//        System.out.println(hashedPassword);
//
//        assertNotNull(hashedPassword);
//    }

}