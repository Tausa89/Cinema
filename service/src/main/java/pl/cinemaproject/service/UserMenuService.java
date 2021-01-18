package pl.cinemaproject.service;


import com.google.common.hash.Hashing;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.User;
import pl.cinemaproject.repository.UserRepository;
import pl.cinemaproject.service.exception.AdminServiceException;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class UserMenuService {


    private final UserRepository userRepository;


    public String addNewUser(@NonNull User user) {

        if (!checkIfUserExist(user)) {
            throw new AdminServiceException("Username or email already exist");

        }

        return userRepository.add(user).orElseThrow().getUsername();


    }


    private boolean checkIfUserExist(@NonNull User user) {


        return userRepository.findByUsername(user.getUsername()).isEmpty() &&
                userRepository.findByEmail(user.getEmail()).isEmpty();


    }


    public String deleteUser(@NonNull User user, @NonNull String password) {


        if (checkUserAndPassword(user, password)) {

            var userToRemove = userRepository.findByUsername(user.getUsername()).orElseThrow();
            return userRepository.deleteById(userToRemove.getId()).orElseThrow().getUsername() + " has been removed";
        }

        throw new AdminServiceException("No record of this user or password is wrong");


    }


    private boolean checkUserAndPassword(User user, String password) {

        return userRepository.findByUsername(user.getUsername()).isPresent() && passwordHashing(password).equals(user.getPassword());
    }


    private String passwordHashing(String password){

        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    public User findByUserName(@NonNull String username){

        if (userRepository.findByUsername(username).isEmpty()) {
            throw new AdminServiceException(username + " dose not exist in data base");
        }

        return userRepository.findByUsername(username).orElseThrow();
    }


    public User userLogin(@NonNull User user, @NonNull String password){

        if(checkUserAndPassword(user, password)){

            return userRepository.findByUsername(user.getUsername()).orElseThrow();
        }

        throw new AdminServiceException("No record of this user or password is wrong");
    }















}
