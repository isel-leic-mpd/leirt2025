package pt.isel.mpd.reflection2.exceptions;

public class ReflectionException extends RuntimeException {
    public ReflectionException(String msg) {
        super(msg);
    }

    public ReflectionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
