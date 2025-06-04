package pt.isel.mpd.functional;

public interface BiFunction<T,U,V> {
    V apply(T t, U u);
    
    //f(a,b) <=> fp.apply(a).apply(b)
     default Function<T, Function<U,V>> curry() {
        return t -> u -> apply(t,u);
     }
    
}
