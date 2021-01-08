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

       //ToDo Polskie znaki
        if(!words.matches("^(\\w*[,]+)*\\w+$")){

            throw new AdminDataException("Wrong Input");
        }

        return words;
    }


    public static boolean getYesOrNo(){

        System.out.println("Do you want to continue or not? If yes Press Y if not press N");
        var yesOrNo = scanner.nextLine();
        if(yesOrNo.toLowerCase().matches("y")){
            return true;
        }else if(yesOrNo.toLowerCase().matches("n")){
            return false;
        }else {
            System.out.println("Wrong Input, use only Y or N");
        }
        return true;

    }





}
