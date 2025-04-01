package pt.isel.mpd.reflection2.exceptions;

public class UnknownProductType extends RuntimeException {
    public UnknownProductType(String typeName) {
        super("Unknown product type: " + typeName);
    }
}
