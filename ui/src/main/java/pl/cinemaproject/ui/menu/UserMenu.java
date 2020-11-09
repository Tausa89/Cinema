package pl.cinemaproject.ui.menu;

import lombok.RequiredArgsConstructor;
import pl.cinemaproject.service.UserService;
import pl.cinemaproject.ui.data.AdminUserDataService;

@RequiredArgsConstructor
public class UserMenu {


    private final UserService userService;




    public void getUserMainMenu(){


        while (true) {

            var option = userMainMenu();
            switch (option){

                case 1 -> getSeancesWithSpecifiedCriteria();


                default -> System.out.println("Wrong input");

            }
        }

    }


    private void getSeancesWithSpecifiedCriteria(){

        var searchCriteria = getSearchCriteriaAsString();
        var listOfCriteria = userService.prepareListOfSearchCriteria(searchCriteria);

        var matchingSeances = userService.findSameWords(listOfCriteria);

        if(matchingSeances.isEmpty()){
            System.out.println("There is no match for given criteria");
        } else
            matchingSeances.forEach(System.out::println);


    }




    private String getSearchCriteriaAsString() {


        System.out.println("""
                Please provide search criteria.
                You can only use letter nad numbers,
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
                Welcome
                If you want to find seance by any category press 1.
                """);

        return AdminUserDataService.getInt("Choose option");
    }

}
