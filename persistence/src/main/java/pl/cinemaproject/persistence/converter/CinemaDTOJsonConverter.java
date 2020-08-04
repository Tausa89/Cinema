package pl.cinemaproject.persistence.converter;

import pl.cinemaproject.persistence.converter.generic.JsonConverter;
import pl.cinemaproject.persistence.modeldto.CinemaComplexDTO;

import java.util.List;

public class CinemaDTOJsonConverter extends JsonConverter <CinemaComplexDTO> {

    public CinemaDTOJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
