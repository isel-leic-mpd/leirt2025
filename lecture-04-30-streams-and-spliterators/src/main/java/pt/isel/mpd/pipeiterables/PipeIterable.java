package pt.isel.mpd.pipeiterables;

import pt.isel.mpd.pipeiterables.iterators.FilterIterator;
import pt.isel.mpd.pipeiterables.iterators.FlatmapIterator;

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
    
    static <T> PipeIterable<T> generate( Supplier<T> generator) {
        return () ->
           new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return true;
                }
                
                @Override
                public T next() {
                    return generator.get();
                }
            };
        
    }
    
    static <T> PipeIterable<T> iterate(T seed,
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
        return () -> new Iterator<T>() {
            Iterator<T> srcIt = iterator();
            Optional<T> next = Optional.empty();
            Set<T> distincts = new HashSet<>();
            
            @Override
            public boolean hasNext() {
                if (next.isPresent()) return true;
                while(srcIt.hasNext()) {
                    var nextSrc = srcIt.next();
                    if (distincts.add(nextSrc)) {
                        next = Optional.of(nextSrc);
                        return true;
                    }
                }
                return false;
            }
            
            @Override
            public T next() {
                if (!hasNext()) throw new IllegalStateException();
                var curr = next.get();
                next = Optional.empty();
                return curr;
            }
        };
    }
    
    
    default PipeIterable<T> limit(int max) {
        return () -> new Iterator<T>() {
            int current = max;
            Iterator<T> srcIt = iterator();
            
            @Override
            public boolean hasNext() {
                return current > 0 && srcIt.hasNext();
            }
            
            @Override
            public T next() {
                if (!hasNext()) throw  new IllegalStateException();
                current--;
                return srcIt.next();
            }
            
        };
    }
    
    default PipeIterable<T> cache() {
        ArrayList<T> buffer = new ArrayList<>();
        Iterator<T> srcIt = iterator();
        return () -> new Iterator<T>() {
            int index = 0;
            
           @Override
           public boolean hasNext() {
               if (index < buffer.size()) return true;
               if (!srcIt.hasNext()) return false;
               buffer.add(srcIt.next());
               return true;
           }
           
           @Override
           public T next() {
               if (!hasNext()) throw new IllegalStateException();
               return buffer.get(index++);
           }
       };
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
