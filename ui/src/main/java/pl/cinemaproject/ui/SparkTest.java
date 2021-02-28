package pl.cinemaproject.ui;

import pl.cinemaproject.repository.generic.DatabaseConnector;
import pl.cinemaproject.repository.impl.UserRepositoryImpl;
import pl.cinemaproject.service.UserService;
import pl.cinemaproject.ui.menu.Routing;

import static spark.Spark.*;

public class SparkTest {

    public static void main(String[] args) {

        final String URL = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        final String USERNAME = "hbstudent";
        final String PASSWORD = "hbstudent";

        var connection = new DatabaseConnector(URL, USERNAME, PASSWORD);
        port(8000);
        init();


        var userRepo = new UserRepositoryImpl(connection);

        var userService = new UserService(userRepo);
        var routing = new Routing(userService);
        routing.routes();
    }
}
