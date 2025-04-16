package pt.isel.mpd.weather2;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.weather2.queries.PipeIterable;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mpd.weather2.queries.PipeIterable.from;
import static pt.isel.mpd.weather2.queries.PipeIterable.generate;

public class PipeIterableTests {
    @Test
    public void cacheTest() {
        Random r = new Random();
        PipeIterable<Integer> nrs = generate(() -> r.nextInt(100));
       // nrs = nrs.cache();
        
        var expected = nrs.limit(10).toList();
        var actual = nrs.limit(10).toList();
        assertEquals(expected, actual);
    }
    
    static class Id {
        public final int id;
        
        public Id(int id) {
            this.id = id;
        }
        
        @Override
        public String toString() {
            return String.format("Id(%d)", id);
        }
        
        @Override
        public boolean equals(Object o) {
            if (o.getClass() != Id.class) return false;
            var other = (Id) o;
            return other.id == id;
        }
        
        @Override
        public int hashCode() {
            return id;
        }
    }
    
    @Test
    public void distinctTest() {
        var ids = List.of(new Id(3), new Id(1), new Id(3), new Id(5));
        Iterable<Id> iter_ids = ids;
     
        from(ids).distinct().forEach(System.out::println);
    }
}
