package pl.cinemaproject.persistence.model.view;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SeancesView {

    private String movieTitle;
    private String dateAndTime;
    private String cinemaRoomName;
    private String cinemaName;
    private String cityName;
    private Integer id;


    @Override
    public String toString() {
        return "City name = " + cityName  + "," +
                " Cinema name = " + cinemaName + "," +
                " Cinema Room Name = " + cinemaRoomName + "," +
                " Movie title = " + movieTitle + "," +
                " Start date and time = " + dateAndTime;
    }
}
