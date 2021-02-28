package pl.cinemaproject.persistence.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cinemaproject.persistence.modeldto.UserResponseDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    private Integer id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;



    public UserResponseDTO toUserResponseDTO(){

        return UserResponseDTO
                .builder()
                .id(id)
                .username(username)
                .build();
    }
}
