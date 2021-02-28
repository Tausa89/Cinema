package pl.cinemaproject.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.User;
import pl.cinemaproject.persistence.modeldto.CreateUserDTO;
import pl.cinemaproject.persistence.modeldto.UserRequestDTO;
import pl.cinemaproject.persistence.modeldto.UserResponseDTO;
import pl.cinemaproject.repository.UserRepository;
import pl.cinemaproject.service.exception.ServiceException;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordService passwordService;


    public UserResponseDTO addNewUser(@NonNull CreateUserDTO createUserDTO) {

        if (!checkIfUserExist(createUserDTO)) {
            throw new ServiceException("Username already exist");

        }

        if (passwordService.checkPassword(createUserDTO.getPassword())) {

            createUserDTO.setPassword(passwordService.passwordHashing(createUserDTO.getPassword()));
        }

        return userRepository
                .add(createUserDTO.toUser())
                .map(User::toUserResponseDTO)
                .orElseThrow(() -> new ServiceException("Cannot add user to database"));


    }


    private boolean checkIfUserExist(@NonNull CreateUserDTO createUserDTO) {


        return userRepository.findByUsername(createUserDTO.getUsername()).isEmpty();


    }


    public String deleteUser(UserRequestDTO userRequestDTO) {


        if (checkUserAndPassword(userRequestDTO.getUsername(), userRequestDTO.getPassword())) {

            var user = userRepository.findByUsername(userRequestDTO.getUsername())
                    .orElseThrow(() -> new ServiceException("User doesn't exist"));

            return userRepository.deleteById(user.toUserResponseDTO()
                    .getId()).orElseThrow().getUsername() + " has been removed";
        }

        throw new ServiceException("No record of this user or password is wrong");


    }


    private boolean checkUserAndPassword(String username, String password) {

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException("User doesn't exist"));

        return Objects.nonNull(user) && passwordService.passwordHashing(password).equals(user.getPassword());
    }


    public User findByUserName(@NonNull String username) {

        if (userRepository.findByUsername(username).isEmpty()) {
            throw new ServiceException(username + " dose not exist in data base");
        }

        return userRepository.findByUsername(username).orElseThrow();
    }


    public User userLogin(@NonNull String userName, @NonNull String password) {

        var user = findByUserName(userName);

        if (checkUserAndPassword(user, password)) {

            return userRepository.findByUsername(user.getUsername()).orElseThrow();
        }

        throw new ServiceException(userName + " dose not exist in data base or password is wrong");

    }


    public List<User> getAllUsers() {

        return userRepository.findAll();
    }


}
