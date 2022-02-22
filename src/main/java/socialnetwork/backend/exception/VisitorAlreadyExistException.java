package socialnetwork.backend.exception;

public class VisitorAlreadyExistException extends GeneralException{

    public VisitorAlreadyExistException() {
        super();
    }

    public VisitorAlreadyExistException(String message) {
        super(message);
    }

    public VisitorAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisitorAlreadyExistException(Throwable cause) {
        super(cause);
    }

    protected VisitorAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
