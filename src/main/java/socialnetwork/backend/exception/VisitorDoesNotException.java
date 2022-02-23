package socialnetwork.backend.exception;

public class VisitorDoesNotException extends GeneralException{
    public VisitorDoesNotException() {
        super();
    }

    public VisitorDoesNotException(String message) {
        super(message);
    }

    public VisitorDoesNotException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisitorDoesNotException(Throwable cause) {
        super(cause);
    }

    protected VisitorDoesNotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
