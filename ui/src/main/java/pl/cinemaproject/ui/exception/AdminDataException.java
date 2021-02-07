package pl.cinemaproject.ui.exception;

public class AdminDataException extends RuntimeException {
    //If I understand correctly it's thrown as "ConsoleUIWrongDataException"
    public AdminDataException(String message) {
        super(message);
    }
}
