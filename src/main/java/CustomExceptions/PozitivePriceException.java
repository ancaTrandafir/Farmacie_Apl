package CustomExceptions;

public class PozitivePriceException extends Exception {
    public PozitivePriceException(String errorMessage) {
        super(errorMessage);

    }
}
