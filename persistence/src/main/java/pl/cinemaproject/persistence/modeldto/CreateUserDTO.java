package pl.cinemaproject.persistence.modeldto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cinemaproject.persistence.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateUserDTO {


    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;



    public User toUser(){

        return User
                .builder()
                .name(name)
                .surname(surname)
                .username(username)
                .password(password)
                .email(email)
                .build();
    }

}
