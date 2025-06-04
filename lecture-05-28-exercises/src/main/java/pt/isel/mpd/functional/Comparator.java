package pt.isel.mpd.functional;

import java.util.function.Function;

@FunctionalInterface
public interface Comparator<T> {
	 
	int compare(T o1, T o2);


	/**
	 * Returns a comparator that imposes the reverse ordering of this
	 * comparator.
	 */
	default Comparator<T> reversed() {
		 return  (t1, t2) ->  compare(t2, t1);  
	}

	/**
	 * If this {@code Comparator} considers two elements equal, i.e.
	 * {@code compare(a, b) == 0}, {@code other} is used to determine the order.
	 */
	default Comparator<T> thenComparing(Comparator<? super T> other) {
		return  null;
	}

	/**
	 * Returns a comparator with a function that
	 * extracts a key to be compared with the given {@code Comparator}.
	 */
	default <U> Comparator<T> thenComparing(
		Function<T, U> keyExtractor,
		Comparator<U> keyComparator)
	{
		return  null;
	}

	/**
	 * Returns a comparator with a function that
	 * extracts a {@code Comparable} sort key.
	 */
	default <U extends Comparable<U>> Comparator<T> thenComparing(
		Function<T, U> keyExtractor)
	{
		return null;
	}
	
	
	/**
	 * Returns a null-friendly comparator that considers {@code null} to be
	 * less than non-null. When both are {@code null}, they are considered
	 * equal. If both are non-null, the specified {@code Comparator} is used
	 * to determine the order. If the specified comparator is {@code null},
	 * then the returned comparator considers all non-null values to be equal.
	 */
	public static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator) {
	   return null;
	}

 

	/**
	 * Accepts a function that extracts a sort key from a type {@code T}, and
	 * returns a {@code Comparator<T>} that compares by that sort key using
	 * the specified {@link Comparator}
	 */
	public static <T, U> Comparator<T> comparing(
		Function<T, U> keyExtractor,
		Comparator<U> keyComparator)
	{
		return null;
	}

	/**
	 * Accepts a function that extracts a {@link Comparable
	 * Comparable} sort key from a type {@code T}, and returns a {@code
	 * Comparator<T>} that compares by that sort key.
	 */
	public static <T, U extends Comparable<U>>
	Comparator<T> comparing(
		Function<T, U> keyExtractor)
	{
		return null;
	  
	}
}
