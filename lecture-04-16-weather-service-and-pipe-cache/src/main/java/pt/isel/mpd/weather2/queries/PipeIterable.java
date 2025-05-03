package pt.isel.mpd.weather2.queries;

import pt.isel.mpd.weather2.queries.iterators.FilterIterator;
import pt.isel.mpd.weather2.queries.iterators.FlatmapIterator;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static pt.isel.mpd.weather2.utils.Errors.TODO;

public interface PipeIterable<T> extends Iterable<T> {
    // Construction Operations
    static <T> PipeIterable<T> from(Iterable<T> src) {
//        return new PipeIterable<T>() {
//            @Override
//            public Iterator<T> iterator() {
//                return src.iterator();
//            }
//        };
        
        return () -> src.iterator();
    }
    
    static <T> PipeIterable<T> generate(Supplier<T> s) {
        TODO("generate");
        return null;
    }
    
    static <T> PipeIterable<T> iterator(T seed,
                                   UnaryOperator<T> nextValue) {
        return () -> new Iterator<T>() {
            T next = seed;
            @Override
            public boolean hasNext() {
                return true;
            }
            
            @Override
            public T next() {
                var curr = next;
                next = nextValue.apply(curr);
                return curr;
            }
        };
        
    }
    // Intermediate Operations
    
    default PipeIterable<T> filter(Predicate<T> pred) {
        return () -> new FilterIterator<>(this, pred);
    }
    
    default <U> PipeIterable<U> map(Function<T,U> mapper) {
        List<U> res = new ArrayList<>();
        for (var e : this) {
            res.add(mapper.apply(e));
        }
        return from(res);
    }
    
    default <U> PipeIterable<U> flatMap(Function<T,Iterable<U>> mapper) {
        return () -> new FlatmapIterator(this, mapper);
        
    }
    
    default PipeIterable<T> distinct() {
        TODO("distinct");
        return null;
    }

    default PipeIterable<T>  cache() {
        TODO("cache");
        return null;
    }
    
    default PipeIterable<T> limit(int n) {
        TODO("limit");
        return null;
    }
    
    // Terminal Operations
    default Optional<T> max(Comparator<T> cmp) {
        T result = null;
        for(var e : this) {
            if (result == null || cmp.compare(e, result) > 0) {
                result = e;
            }
        }
        return Optional.ofNullable(result);
    }
    
    default long count() {
        long result = 0;
        for(var e : this) {
            result++;
        }
        return  result;
    }
    
    default List<T> toList() {
        var list = new ArrayList<T>();
        forEach(e -> list.add(e));
        return list;
    }
}
