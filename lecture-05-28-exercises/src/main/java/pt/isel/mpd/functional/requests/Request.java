package pt.isel.mpd.functional.requests;

import java.io.Reader;
import java.io.StringWriter;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;


public interface Request {
    Reader get(String path);
    
    default Request chain(BiFunction<String, Reader, Reader> action) {
        return null;
    }
}
