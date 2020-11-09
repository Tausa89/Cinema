package pl.cinemaproject.ui.data;

import pl.cinemaproject.ui.exception.AdminDataException;

import java.util.Scanner;

public final class AdminUserDataService {


    public AdminUserDataService() {
    }

    private static final Scanner scanner = new Scanner(System.in);



    public static int getInt(String message){

        System.out.println(message);

        String value = scanner.nextLine();

        if(!value.matches("[0-9]*")){
            throw new AdminDataException("Wrong input");
        }

        return Integer.parseInt(value);
    }


    public static String getString(String message){

        System.out.println(message);

        return scanner.nextLine();
    }


    public static String getSearchCriteria(String message){

        System.out.println(message);

       var words = scanner.nextLine();

        if(!words.matches("^(\\w+[,]+)*\\w+$")){

            throw new AdminDataException("Wrong Input");
        }

        return words;
    }





}
