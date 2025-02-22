package pt.isel.mpd;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.exceptions.DivByZeroException;
import pt.isel.mpd.expressions.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionTests {
    @Test
    public void simpleAddTest() {
        var expr = new Add(new Const(3), new Const(5));
        assertEquals(8, expr.eval());
        assertEquals("3.0+5.0", expr.getFormula());
    }
    
    @Test
    public void moreComplexExpression() {
        var expr = new Mul(
                     new Const(3),
                     new Mul(
                         new Const(-4),
                         new Sub(
                             new Const(2),
                             new Const(3)
                         )
                     )
                   );
        assertEquals(12, expr.eval());
        assertEquals("3.0*(-4.0*(2.0-3.0)", expr.getFormula());
    }
    
    @Test
    public void divByZeroTest() {
        boolean caught = false;
        var expr = new Mul(
                      new Const(7),
                      new Div(
                          new Const(3),
                          new Const(0)
                      )
                     );
        try {
            expr.eval();
        }
        catch(DivByZeroException e) {
            caught = true;
        }
        assertTrue(caught);
    }
    
    @Test
    public void divByZeroUsingJunitAssertThrowsTest() {
        boolean caught = false;
        var expr = new Mul(
            new Const(7),
            new Div(
                new Const(3),
                new Const(0)
            )
        );
        assertThrows(DivByZeroException.class, () -> expr.eval());
    }
}
