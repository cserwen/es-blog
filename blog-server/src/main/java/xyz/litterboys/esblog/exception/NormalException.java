package xyz.litterboys.esblog.exception;

public class NormalException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 2470420794348253802L;

    public NormalException() {
        super();
    }

    public NormalException(String message) {
        super(message);
    }

    public NormalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NormalException(Throwable cause) {
        super(cause);
    }

    protected NormalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
