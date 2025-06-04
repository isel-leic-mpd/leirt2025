package pt.isel.mpd.sequences;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mpd.sequences.Sequence.from;

public class SequenceTests {
    @Test
    public void filterTest() {
        var values = List.of(3, 7, 8, 2, 9, 1);
        
        var seq = from(values)
                                   .filter(t -> t % 2 == 1);
        var expected = List.of(3, 7, 9, 1);
        assertEquals(expected, seq.toList());
    }
}
