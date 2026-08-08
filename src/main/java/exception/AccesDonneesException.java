package exception;

public class AccesDonneesException extends RuntimeException {
    public AccesDonneesException(String message, Throwable cause) {
        super(message, cause);
    }
}