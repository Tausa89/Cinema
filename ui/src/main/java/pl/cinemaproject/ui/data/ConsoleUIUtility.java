package pl.cinemaproject.ui.data;

import com.google.common.hash.Hashing;
import pl.cinemaproject.ui.exception.ConsoleUIException;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class ConsoleUIUtility {


    public ConsoleUIUtility() {
    }

    private static final Scanner scanner = new Scanner(System.in);


    public static int getInt(String message) {

        System.out.println(message);

        String value = scanner.nextLine();

        if (!value.matches("[0-9]*")) {
            throw new ConsoleUIException("Wrong input");
        }

        return Integer.parseInt(value);
    }


    public static String getString(String message) {

        System.out.println(message);

        return scanner.nextLine();
    }


    public static String getSearchCriteria(String message) {

        System.out.println(message);

        var words = scanner.nextLine();

        //ToDo Polskie znaki
        if (!words.matches("^(\\w*[,]+)*\\w+$")) {

            throw new ConsoleUIException("Wrong Input");
        }

        return words;
    }


    public static boolean getYesOrNo(String msg) {

        System.out.println(msg);
        var yesOrNo = scanner.nextLine();
        if (yesOrNo.toLowerCase().matches("y")) {
            return true;
        } else if (yesOrNo.toLowerCase().matches("n")) {
            return false;
        } else {
            System.out.println("Wrong Input, use only Y or N");
        }
        return true;

    }

    public static String getEmailAddress(String msg) {

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        System.out.println(msg);

        var email = scanner.nextLine();

        if (!email.matches(regex)) {

            throw new ConsoleUIException("Email address is incorrect");

        }

        return email;
    }


    public static String checkPassword(){



        System.out.println("""
                
                Now you need to create a password
                
                1. Password must contain at least one digit.
                2. Password must contain at least one lowercase character.
                3. Password must contain at least one uppercase character.
                4. Password must contain at least one special character like ! @ # & ( ).
                5. Password must contain a length of at least 6 characters and a maximum of 20 characters.
                """);

        var passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

        var password = getString("Please provied your password");
        if (!password.matches(passwordRegex)){
            throw new ConsoleUIException("Password is not strong enough remember about 5 rules");

        }
        var repeatedPassword = getString("Repeat your password");

        if(!password.equals(repeatedPassword)){
            throw new ConsoleUIException("Passwords are not this same");
        }

        return passwordHashing(password);

    }


    private static String passwordHashing(String password){

        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8)
                .toString();
    }


}
