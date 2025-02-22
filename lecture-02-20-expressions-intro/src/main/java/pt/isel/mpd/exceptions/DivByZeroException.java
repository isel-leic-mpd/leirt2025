package pt.isel.mpd.exceptions;

public class DivByZeroException extends ParserException {
    public DivByZeroException() {
        super("Divison by Zero!");
    }
}
