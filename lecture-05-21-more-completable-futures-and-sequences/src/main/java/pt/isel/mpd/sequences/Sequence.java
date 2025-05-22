package pt.isel.mpd.sequences;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static pt.isel.mpd.completable_futures.utils.Errors.TODO;

public interface Sequence<T> {
    boolean tryAdvance(Consumer<T> action);
    
    static <T> Sequence<T> from(Iterable<T> src) {
        var it = src.iterator();
        return action -> {
            if (it.hasNext()) {
                action.accept(it.next());
                return true;
            }
            else {
                return false;
            }
        };
    }
    
    static <T> Sequence<T> empty() {
        return action -> false;
    }
    
    default <U> Sequence<U> map(Function<T,U> mapper) {
        return action -> tryAdvance(t ->
                            action.accept(mapper.apply(t))
                         );
    }
    
    default Sequence<T> filter(Predicate<T> pred)  {
        return action -> {
            boolean[] done = {false};
            while(!done[0] && tryAdvance(t -> {
                if (pred.test(t)) {
                    action.accept(t);
                    done[0] = true;
                }
            }));
            return done[0];
        };
    }
    
    // terminal operations
    default List<T> toList() {
        ArrayList<T> res = new ArrayList<>();
        while(tryAdvance(t -> res.add(t)));
        return res;
    }
}
