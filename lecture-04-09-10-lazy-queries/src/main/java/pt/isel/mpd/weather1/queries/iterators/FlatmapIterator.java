package pt.isel.mpd.weather1.queries.iterators;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

public class FlatmapIterator<T,U> implements Iterator<U> {
    private Iterator<U> currIt;
    private final Iterator<T> srcIt;
    private Optional<U> nextValue = Optional.empty();
    private boolean done;
    private Function<T,Iterable<U>> mapper;
    
    public FlatmapIterator(Iterable<T> src, Function<T,Iterable<U>> mapper) {
        srcIt = src.iterator();
        if (srcIt.hasNext()) {
            currIt = mapper.apply(srcIt.next()).iterator();
            done = false;
        }
        else done = true;
        this.mapper = mapper;
    }
    
    @Override
    public boolean hasNext() {
        if (done) return false;
        if (nextValue.isPresent()) return true;
        while(!currIt.hasNext()) {
            if (!srcIt.hasNext()) {
                done = true;
                return false;
            }
            currIt = mapper.apply(srcIt.next()).iterator();
        }
        nextValue = Optional.of(currIt.next());
        return true;
        
    }
    
    @Override
    public U next() {
        if (!hasNext()) throw new IllegalStateException();
        var curr = nextValue.get();
        nextValue = Optional.empty();
        return curr;
    }
}
