package pt.isel.mpd.functional;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionTests {
    
    @Test
    public void curryTest() {
        BiFunction<String, String, Integer> sumLengths = (String s1, String s2) ->
            s1.length() + s2.length();
        
        var currySum = sumLengths.curry();
        
        assertEquals(sumLengths.apply("Ben", "Sport"),
            currySum.apply("Ben").apply("Sport"));
    }
    
   
    
}
