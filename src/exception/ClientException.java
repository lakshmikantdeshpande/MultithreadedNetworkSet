package exception;

public class ClientException extends RuntimeException {

    public ClientException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
