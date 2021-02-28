package pl.cinemaproject.service;

import com.google.common.hash.Hashing;
import pl.cinemaproject.service.exception.ServiceException;

import java.nio.charset.StandardCharsets;

public class PasswordService {




    public boolean checkPassword(String password){




//        1. Password must contain at least one digit.
//        2. Password must contain at least one lowercase character.
//        3. Password must contain at least one uppercase character.
//        4. Password must contain at least one special character like ! @ # & ( ).
//        5. Password must contain a length of at least 6 characters and a maximum of 20 characters.

        var passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";


        if (!password.matches(passwordRegex)){
            throw new ServiceException("Invalid password");

        }

        return true;

    }


    public String passwordHashing(String password){

        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
