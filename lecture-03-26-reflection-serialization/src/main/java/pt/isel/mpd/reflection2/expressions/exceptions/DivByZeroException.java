package pt.isel.mpd.reflection2.expressions.exceptions;

import pt.isel.mpd.reflection2.expressions.exceptions.ParserException;

public class DivByZeroException extends ParserException {
    public DivByZeroException() {
        super("Divison by Zero!");
    }
}
