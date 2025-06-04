package pt.isel.mpd.functional;


public interface Function<T,U> {
    U apply(T t);
    
    /*Returns a function that always returns its input argument.*/
    static <T> Function<T,T>	identity() {
        return null;
        // to implement
    }
    
    /*
        Returns a composed function that first applies this function to
        its input, and then applies the after function to the result.
     */
    default <V> Function<T,V> andThen(Function<U,V> after) {
        // to implement
        return null;
    }
    
    /*
        Returns a composed function that first applies
        the before function to its input, and then applies this function to the result.
     */
    default <V> Function<V,U> compose(Function<V,T> before) {
        // to implement
        return null;
    }
}