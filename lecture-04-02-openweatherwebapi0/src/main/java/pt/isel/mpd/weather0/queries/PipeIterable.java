package pt.isel.mpd.weather0.queries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface PipeIterable<T> extends Iterable<T> {

    default Iterable<T> filter(Iterable<T> src, Predicate<T> pred) {
        List<T> res = new ArrayList<>();
        for (var e : src) {
            if (pred.test(e)) {
                res.add(e);
            }
        }
        return res;
    }
    
    default Optional<T> max(Iterable<T> src, Comparator<T> cmp) {
        T result = null;
        for(var e : src) {
            if (result == null || cmp.compare(e, result) > 0) {
                result = e;
            }
        }
        return Optional.ofNullable(result);
    }
}
