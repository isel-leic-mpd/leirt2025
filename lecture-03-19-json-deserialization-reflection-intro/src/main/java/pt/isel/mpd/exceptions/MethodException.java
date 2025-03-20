package pt.isel.mpd.exceptions;

public class MethodException extends RuntimeException {
    public MethodException(Exception cause) {
        super("Invoke method error", cause);
    }
    
    public MethodException(String msg) {
        super(msg);
    }
}
