package socialnetwork.backend.exception;

public class EmptyMessageException extends GeneralException {
    public EmptyMessageException() {
        super();
    }

    public EmptyMessageException(String message) {
        super(message);
    }

    public EmptyMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyMessageException(Throwable cause) {
        super(cause);
    }

    protected EmptyMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
