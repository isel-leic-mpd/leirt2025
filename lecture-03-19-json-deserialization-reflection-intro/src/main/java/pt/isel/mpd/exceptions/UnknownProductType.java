package pt.isel.mpd.exceptions;

public class UnknownProductType extends RuntimeException {
    public UnknownProductType(String typeName) {
        super("Unknown product type: " + typeName);
    }
}
