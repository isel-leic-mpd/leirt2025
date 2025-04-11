package pt.isel.mpd.weather1.queries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Queries {

    // Intermediate Operations
    public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> pred) {
        List<T> res = new ArrayList<>();
        for (var e : src) {
            if (pred.test(e)) {
                res.add(e);
            }
        }
        return res;
    }
    
    public static <T,U> Iterable<U> map(Iterable<T> src, Function<T,U> mapper) {
        List<U> res = new ArrayList<>();
        for (var e : src) {
            res.add(mapper.apply(e));
        }
        return res;
    }
    
   
    // Terminal Operations
    public static <T> Optional<T> max(Iterable<T> src, Comparator<T> cmp) {
        T result = null;
        for(var e : src) {
            if (result == null || cmp.compare(e, result) > 0) {
                result = e;
            }
        }
        return Optional.ofNullable(result);
    }
    
    public static <T> long count(Iterable<T> src) {
        long result = 0;
        for(var e : src) {
            result++;
        }
        return  result;
    }
}
