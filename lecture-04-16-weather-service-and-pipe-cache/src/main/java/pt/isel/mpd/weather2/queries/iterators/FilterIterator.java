package pt.isel.mpd.weather2.queries.iterators;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

public class FilterIterator<T>  implements Iterator<T> {
    private final  Iterator<T> srcIt;
    private final Predicate<T> pred;
    private Optional<T> nextValue = Optional.empty();
    
    public FilterIterator(Iterable<T> src, Predicate<T> pred) {
        srcIt = src.iterator();
        this.pred = pred;
    }
    
    @Override
    public boolean hasNext() {
        if (nextValue.isPresent()) return true;
        while(srcIt.hasNext()) {
            T nextVal = srcIt.next();
            if (pred.test(nextVal))  {
                nextValue = Optional.of(nextVal);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public T next() {
        if (!hasNext()) throw new IllegalStateException();
        T curr = nextValue.get();
        nextValue = Optional.empty();
        return curr;
    }
}
