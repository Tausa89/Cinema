package pl.cinemaproject.ui.menu;


import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import pl.cinemaproject.service.UserService;

import static spark.Spark.*;

@RequiredArgsConstructor
public class Routing {


    private final UserService userService;


    public void routes() {


        var gson = new GsonBuilder().setPrettyPrinting().create();


        path("/users", () -> {

            get("",
                    (request, response) -> {

                        var headers = request.headers();
                        for (var header : headers) {
                            System.out.println(header);
                        }

                        var users = userService.getAllUsers();
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return users;
                    },
                    new JsonTransformer()
                    );



            path("/user", () -> {
                get(
                        "/:user",
                        (request, response) -> {
                            var name = request.params(":user");
                            var user = userService.findByUserName(name);
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return user;
                        },
                        new JsonTransformer()
                );
            });

        });

    }

}
