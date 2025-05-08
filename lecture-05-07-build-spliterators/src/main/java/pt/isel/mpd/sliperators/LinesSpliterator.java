package pt.isel.mpd.sliperators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class LinesSpliterator extends Spliterators.AbstractSpliterator<String> {
    
    private BufferedReader reader;
    private boolean closed;
    
    
    public LinesSpliterator(Reader reader) {
        super(Long.MAX_VALUE, ORDERED | NONNULL);
        this.reader = new BufferedReader(reader);
    }
    
    public void close() {
        if (closed) return;
        try {
            reader.close();
        }
        catch(IOException e) {
        }
        finally {
            closed = true;
        }
    }
    
    private String getNext() {
        try {
            if (closed) return null;
            var line = reader.readLine();
            if (line == null) {
                close();
            }
            return line;
        }
        catch(IOException e) {
            close();
            return null;
        }
    }
    
    @Override
    public boolean tryAdvance(Consumer<? super String> action) {
        String line;
        if ((line = getNext()) == null) return false;
        action.accept(line);
        return true;
    }
}
