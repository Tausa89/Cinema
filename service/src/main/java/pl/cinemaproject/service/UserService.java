package pl.cinemaproject.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.view.SeancesView;
import pl.cinemaproject.repository.SeancesViewRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserService {


    private final SeancesViewRepository cinemaCityRoomsViewRepository;


    public List<SeancesView> getAllSeances() {

        return cinemaCityRoomsViewRepository.findCitiesWithCinemasAndCinemaRooms();
    }


    public List<String> convertSeancesToListOfString() {

        return getAllSeances()
                .stream()
                .map(SeancesView::toString)
                .collect(Collectors.toList());
    }


    public List<String> findSameWords(@NonNull List<String> listOfWords) {


        return
                convertSeancesToListOfString()
                        .stream()
                        .filter(s -> findWord(s, listOfWords))
                        .collect(Collectors.toList());


    }


    private boolean findWord(@NonNull String sentence, @NonNull List<String> listOfWords) {


        return listOfWords.stream().anyMatch(sentence::contains);


    }


    public List<String> prepareListOfSearchCriteria(@NonNull String words) {

        return convertWordsToListOfWords(getWordsSeparateWithWhitespaces(words));


    }

    private List<String> convertWordsToListOfWords(@NonNull String words) {

        return Arrays.asList(words.split(" "));
    }


    private String getWordsSeparateWithWhitespaces(@NonNull String words) {

        return words.replace(",", " ");
    }
}
