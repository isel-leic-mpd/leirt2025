package pt.isel.mpd.reflection2.exceptions;

public class NonCompatibleTypesException extends ReflectionException {
    private Class<?> to;
    private Class<?> from;
    
    public NonCompatibleTypesException(Class<?> to, Class<?> from) {
        super("Non compatible classes");
        this.to = to; this.from = from;
    }
}
