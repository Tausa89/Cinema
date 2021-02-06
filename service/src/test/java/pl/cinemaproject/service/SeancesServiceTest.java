package pl.cinemaproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.cinemaproject.persistence.model.view.SeancesView;
import pl.cinemaproject.repository.SeancesViewRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SeancesServiceTest {



    @Mock
    private SeancesViewRepository cinemaCityRoomsViewRepository;

    @InjectMocks
    private SeancesService seancesService;




    @Test
    void shouldConvertSeancesListToStringList(){

        //given




        //when
        Mockito.when(seancesService.getAllSeances()).thenReturn(List.of(
                SeancesView.builder()
                        .cityName("OneCity")
                        .cinemaName("OneCinema")
                        .cinemaRoomName("CinemaRoomOne")
                        .movieTitle("RandomMovie")
                        .dateAndTime("2020-12-01 21:00:00")
                        .build(),
                SeancesView.builder()
                        .cityName("TwoCity")
                        .cinemaName("TwoCinema")
                        .cinemaRoomName("CinemaRoomTwo")
                        .movieTitle("RareMovie")
                        .dateAndTime("2020-10-02 19:30:00")
                        .build(),
                SeancesView.builder()
                        .cityName("ThreeCity")
                        .cinemaName("ThreeCinema")
                        .cinemaRoomName("CinemaRoomThree")
                        .movieTitle("EpicMovie")
                        .dateAndTime("2020-12-20 15:00:00")
                        .build()));

        var result = seancesService.convertSeancesToListOfString();



        //then
        assertEquals(3, result.size());
        assertNotNull(result);




    }


    @Test
    void shouldReturnSeancesWithNumbers(){

        var list = List.of(
                SeancesView.builder()
                        .cityName("OneCity")
                        .cinemaName("OneCinema")
                        .cinemaRoomName("CinemaRoomOne")
                        .movieTitle("RandomMovie")
                        .dateAndTime("2020-12-01 21:00:00")
                        .build(),
                SeancesView.builder()
                        .cityName("TwoCity")
                        .cinemaName("TwoCinema")
                        .cinemaRoomName("CinemaRoomTwo")
                        .movieTitle("RareMovie")
                        .dateAndTime("2020-10-02 19:30:00")
                        .build(),
                SeancesView.builder()
                        .cityName("ThreeCity")
                        .cinemaName("ThreeCinema")
                        .cinemaRoomName("CinemaRoomThree")
                        .movieTitle("EpicMovie")
                        .dateAndTime("2020-12-20 15:00:00")
                        .build());

        var result = seancesService.prepareSeancesListWithNumbers(list);

        System.out.println(result);


    }

    //ToDo czemu nie działą
//    @Test
//    void shouldFindSameSymbols(){
//
//
//
//
//
//
//        List<String> words = List.of("One", "21");
//
//        //when
//        Mockito.when(userService.convertSeancesToListOfString()).thenReturn(List.of("City name = OneCity, Cinema name = OneCinema, Cinema Room Name = " +
//                        "CinemaRoomOne, Movie title = RandomMovie, Start date and time = 2020-12-01 21:00:00",
//                "City name = TwoCity, Cinema name = TwoCinema, Cinema Room Name = " +
//                        "CinemaRoomTwo, Movie title = RareMovie, Start date and time = 2020-10-02 19:30:00",
//                "City name = ThreeCity, Cinema name = ThreeCinema, Cinema Room Name = " +
//                        "CinemaRoomThree, Movie title = EpicMovie, Start date and time = 2020-12-20 15:00:00"));
//        var result = userService.findSameWords(words);
//
//        result.forEach(System.out::println);
//
//
//        //then
//        assertNotNull(result);
//        assertEquals(1,result.size());
//    }

//    @Test
//    void shouldFindSameWords(){
//
//        //Given
//        List<String> words = List.of("One", "Three");
//
//        String seans = "City name = OneCity, Cinema name = OneCinema, Cinema Room Name = " +
//                "CinemaRoomOne, Movie title = RandomMovie, Start date and time = 2020-12-01 21:00:00";
//
//
//        //when
//        var result = userService.findWord(seans,words);
//
//        assertTrue(result);
//
//    }


    //ToDo zapytac czy takie testy powinny zostać
//    @Test
//    void  shouldConvertStringToListOfWords(){
//
//        String randomWordsAndSigns = "One Z 21 Hermetic";
//
//        var result = userService.convertStringWordsToListOfWords(randomWordsAndSigns);
//
//
//        assertNotNull(result);
//        assertEquals(4, result.size());
//    }
//
//
//    @Test
//    void  shouldConvertCommaToWhitespaces(){
//
//
//        String wordsSeparateWithCommas = "One,Z,21,Hermetic";
//
//        var result = userService.getWordsSeparateWithWhitespaces(wordsSeparateWithCommas);
//
//        assertFalse(result.contains(","));
//    }




}