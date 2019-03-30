package CustomExceptions;

public class NonUniqueCNPException extends Exception {
    public NonUniqueCNPException(String errorMessage) {
        super(errorMessage);
    }
}