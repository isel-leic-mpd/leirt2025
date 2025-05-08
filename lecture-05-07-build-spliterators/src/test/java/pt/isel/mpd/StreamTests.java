package pt.isel.mpd;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.stream.Stream;

public class StreamTests {
    @Test
    public void streamFromReaderLines() {
        var text = """
                first
                second
                third
                """;
        var reader = new StringReader(text);
        try( var lines = StreamUtils.lines(reader)) {
            lines
                .forEach(System.out::println);
        }
        
    }
    
    
    
    
    
    
    
    @Test
    public void spliteratorFromStream() {
        var stream = Stream.of(1,2,3);
        var streamIter = stream.spliterator();
        //var streamIter2 = stream.spliterator();
        
        while(streamIter.tryAdvance(System.out::println));
        while(streamIter.tryAdvance(System.out::println));
    
    }
}
