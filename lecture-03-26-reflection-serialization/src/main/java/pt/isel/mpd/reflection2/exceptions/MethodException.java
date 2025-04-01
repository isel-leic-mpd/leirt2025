package pt.isel.mpd.reflection2.exceptions;

public class MethodException extends ReflectionException {
    public MethodException(Exception cause) {
        super("Invoke method error", cause);
    }
    
    public MethodException(String msg) {
        super(msg);
    }
}
