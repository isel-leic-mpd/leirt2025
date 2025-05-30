package pt.isel.mpd.pipeiterables;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CacheTests {
    @Test
    public void randomGeneratedSequence() {
        var random = new Random(System.currentTimeMillis());
        var randomNumbers = PipeIterable.generate(()->random.nextLong(1000));
        
        var randoms1 = randomNumbers.limit(10).toList();
        var randoms2 = randomNumbers.limit(20).toList();
        randoms1.forEach(System.out::println);
        System.out.println();
        randoms2.forEach(System.out::println);
        assertNotEquals(randoms1, randoms2);
    }
    
    @Test
    public void cacheFromRandomGeneratedSequence() {
        
        var random = new Random(System.currentTimeMillis());
        var randomNumbers = PipeIterable.generate(()->random.nextLong(1000));
        
        var randomCache = randomNumbers.cache();
        
        var randoms1 = randomCache.limit(10).toList();
        var randoms2 = randomCache.limit(10).toList();
        
        randoms1.forEach(System.out::println);
        System.out.println();
        randoms2.forEach(System.out::println);
        
        assertEquals(randoms1, randoms2);
        
       
        randoms1 = randomCache.limit(20).toList();
        randoms2 = randomCache.limit(20).toList();
        System.out.println();
        randoms1.forEach(System.out::println);
        System.out.println();
        randoms2.forEach(System.out::println);
        
        assertEquals(randoms1, randoms2);
        
    }
    
    @Test
    public void cacheFromRandomGeneratedSequenceSameSeed() {
        
        var random0 = new Random(5);
        var randomNumbers0 = PipeIterable.generate(()->random0.nextLong(1000));
        var randoms0 = randomNumbers0.limit(10).toList();
        
        var random = new Random(5);
        var randomNumbers = PipeIterable.generate(()->random.nextLong(1000));
        
        var randomCache = randomNumbers.cache();
        
        var randoms1 = randomCache.limit(10).toList();
        var randoms2 = randomCache.limit(10).toList();
        
        
        assertEquals(randoms1, randoms2);
        assertEquals(randoms0, randoms1);
        
    }
    
    @Test
    public void cacheFromRandomGeneratedSequenceSameSeed2() {
        
        var random0 = new Random(5);
        var randomNumbers0 = PipeIterable.generate(()->random0.nextLong(1000));
        var randoms0 = randomNumbers0.limit(10).toList();
        
        var random = new Random(5);
        var randomNumbers = PipeIterable.generate(()->random.nextLong(1000));
        
        var randomCache = randomNumbers.cache();
        
        var randoms1 = randomCache.iterator();
        var randoms2 = randomCache.iterator();
        

        
        
        assertEquals(randoms1, randoms2);
        assertEquals(randoms0, randoms1);
        
    }
}
