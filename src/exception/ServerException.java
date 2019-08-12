package exception;

public class ServerException extends RuntimeException {

    public ServerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
