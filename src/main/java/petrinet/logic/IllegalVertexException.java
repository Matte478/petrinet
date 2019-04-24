package petrinet.logic;

public class IllegalVertexException extends Exception {
    public IllegalVertexException() {
    }

    public IllegalVertexException(String message) {
        super(message);
    }

    public IllegalVertexException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalVertexException(Throwable cause) {
        super(cause);
    }

    public IllegalVertexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
