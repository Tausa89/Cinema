package pl.cinemaproject.ui.data;

import pl.cinemaproject.ui.exception.AdminDataException;

import java.util.Scanner;

public final class AdminDataService {


    public AdminDataService() {
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



}
