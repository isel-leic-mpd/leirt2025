package pt.isel.mpd;

//import pt.isel.mpd.more_streams.sliperators.LinesSpliterator;

import pt.isel.mpd.sliperators.LinesSpliterator;

import java.io.Reader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;



public class StreamUtils {
    public static Stream<String> lines(Reader reader) {
       var spliterator = new LinesSpliterator(reader);
       var stream = StreamSupport.stream(spliterator, false);
       stream.onClose(() -> spliterator.close());
       return stream;
    }
}
