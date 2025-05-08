package pt.isel.mpd;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.sequences.Sequence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static pt.isel.mpd.sequences.Sequence.*;
import  pt.isel.mpd.sequences.*;

public class SequenceTests {
    @Test
    public void emptySequenceTest() {
        var seq = empty();
        
        assertFalse(seq.tryAdvance((t) -> {
            System.out.println("Não é suposto chegar aqui!");
        }));
    }
    
    @Test
    public void fromIterableSequenceTest() {
        var elems = List.of(1,2,3);
        
        var seq = from(elems);
        
        while(seq.tryAdvance(t -> System.out.println(t)));
    }
}
