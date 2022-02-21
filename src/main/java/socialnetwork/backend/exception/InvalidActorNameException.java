package socialnetwork.backend.exception;

public class InvalidActorNameException extends GeneralException{
    public InvalidActorNameException() {
        super();
    }

    public InvalidActorNameException(String message) {
        super(message);
    }

    public InvalidActorNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidActorNameException(Throwable cause) {
        super(cause);
    }

    protected InvalidActorNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
