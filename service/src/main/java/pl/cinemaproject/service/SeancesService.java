package pl.cinemaproject.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.cinemaproject.persistence.model.view.SeancesView;
import pl.cinemaproject.repository.SeancesViewRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SeancesService {
    private final SeancesViewRepository cinemaCityRoomsViewRepository;

    //this 4 functions should be in repository i think, no need to make such proxy
    public List<SeancesView> getAllSeances() {
        return cinemaCityRoomsViewRepository.getAllSeancesWithAllDate();
    }

    public List<SeancesView> getSeancesForSpecifiedCity(String cityName){
        return cinemaCityRoomsViewRepository.getAllSeancesForGivenCity(cityName);
    }

    public List<SeancesView> getSeancesForSpecifiedCinema(String cinemaName){
        return cinemaCityRoomsViewRepository.getAllSeancesForGivenCinema(cinemaName);
    }

    public List<SeancesView> getSeancesForSpecifiedMovie(String movieName){
        return cinemaCityRoomsViewRepository.getAllSeancesForGivenMovie(movieName);
    }

    //ok, but should get seances as argument
    public List<String> convertSeancesToListOfString() {
        return getAllSeances()
                .stream()
                .map(SeancesView::toString)
                .collect(Collectors.toList());
    }

    // no, no, no, it's not related to seances at all, and it's not needed for anything except console
    // move it to some console ui related class
    public List<String> findSameWords(@NonNull List<String> listOfWords) {
        return
                convertSeancesToListOfString()
                        .stream()
                        .filter(s -> findWord(s, listOfWords))
                        .collect(Collectors.toList());
    }

    // it's not related to seances at all, and it's not needed for anything except console
    // move it to some console ui related class
    private boolean findWord(@NonNull String sentence, @NonNull List<String> listOfWords) {
        return listOfWords.stream().anyMatch(sentence::contains);
    }

    //this 3 functions below could be merged to one
    public List<String> prepareListOfSearchCriteria(@NonNull String words) {
        return convertWordsToListOfWords(getWordsSeparateWithWhitespaces(words));
    }

    private List<String> convertWordsToListOfWords(@NonNull String words) {
        return Arrays.asList(words.split(" "));
    }

    private String getWordsSeparateWithWhitespaces(@NonNull String words) {
        return words.replace(",", " ");
    }

    //again - console related function should be in console related classes
    public List<String> prepareSeancesListWithNumbers(List<SeancesView> seances){
        return seances.stream().map(s -> seances.indexOf(s) + 1 + " " + s.toString() + "\n").collect(Collectors.toList());
    }
}
