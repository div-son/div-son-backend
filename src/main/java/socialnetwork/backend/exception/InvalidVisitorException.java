package socialnetwork.backend.exception;

public class InvalidVisitorException extends GeneralException{

    public InvalidVisitorException() {
        super();
    }

    public InvalidVisitorException(String message) {
        super(message);
    }

    public InvalidVisitorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVisitorException(Throwable cause) {
        super(cause);
    }

    protected InvalidVisitorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
