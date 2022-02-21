package socialnetwork.backend.exception;

public class ActorAlreadyExistException extends GeneralException{

    public ActorAlreadyExistException() {
        super();
    }

    public ActorAlreadyExistException(String message) {
        super(message);
    }

    public ActorAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActorAlreadyExistException(Throwable cause) {
        super(cause);
    }

    protected ActorAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
