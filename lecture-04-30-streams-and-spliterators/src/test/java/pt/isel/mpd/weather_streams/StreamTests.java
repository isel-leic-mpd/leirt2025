package pt.isel.mpd.weather_streams;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamTests {
    
    @Test
    public void showFirst5Primes() {
        var primes = Stream.iterate(2L, PrimeUtils::nextPrime);
        
        var listPrimes = primes
            .limit(5)
            .toList();
            
        listPrimes.forEach(System.out::println);
        
        // the assertion fails because the stream admits only one terminal operation
        // In this case, the foreach above
        assertEquals(5, listPrimes.size());
    }
    
    @Test
    public void showPrimesLessThan20() {
        var primes = Stream.iterate(2L, PrimeUtils::nextPrime);
        primes
            .peek(p -> System.out.println(p))
            .takeWhile(p -> p < 20)
            .forEach(System.out::println);
        
        // the assertion fails because the stream admits only one terminal operation
        // In this case, the foreach above
        //assertEquals(5, primes.count());
    }
    
    @Test
    public void getPrimesTill_10_000_000() {
        
        var primes =
            LongStream.range(2, 1_000_000)
                .mapToObj(l -> l)
                .filter(PrimeUtils::isPrime);
        
        long startTime = System.currentTimeMillis();
        
        var primesArray =
            // the parallel operation speedup the
            // stream consume by using all cores
            // in the process
            primes
            .parallel().
            toArray();
        
        System.out.println("done in " + (System.currentTimeMillis()-startTime) + "ms!");
        
        System.out.println("number of primes till 10_000_000: " + primesArray.length);
        for(var p : primesArray) {
            System.out.println(p);
        }
      
    }
    
    class Pair<T,U> {
        public final T first;
        public final U second;
        
        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
        
        @Override
        public String toString()  {
            return String.format("{%s, %s}", first, second);
        }
    }
    
    @Test
    public void produceCombinationsPairsOfIntsBteween1And10() {
        var combs =
            IntStream.rangeClosed(1,10).boxed() //mapToObj(n -> n)
                .flatMap(n1 ->
                    IntStream.rangeClosed(n1 + 1, 10).boxed()
                    .map(n2 -> new Pair(n1, n2))
                );
        
        var combsList = combs.toList();
        
        for(var p : combsList) {
            System.out.println(p);
        }
        
        System.out.println("combs number = " + combsList.size());
    }
    
    
    @Test
    public void getSpliteratorStreamTest() {
        var names =
            Stream.of("Leandro", "Paulo", "Leonel")
                .peek(System.out::println)
                .filter(name -> name.startsWith("L"));
        
        System.out.println("Start!");
        var streamIt = names.spliterator();
        
        // iterate it!
        // same as: streamIt.forEachRemaining(System.out::println));
        while(streamIt.tryAdvance(System.out::println));
        
    }
    
    
    
}
