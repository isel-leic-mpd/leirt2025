package pt.isel.mpd;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.parser.Lex;
import pt.isel.mpd.parser.Lex.*;
import pt.isel.mpd.parser.ParserExpr;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTests {
    
    @Test
    public void testLexWithExpressions() {
        String exprText = "23 + 3 * (40+80)";
        
        Lex lex = new Lex();
        
        lex.start(exprText);
        
        Lex.Token tok;
        do {
            tok = lex.next();
            tok.show();
        }
        while(tok.getType() != TokType.END);
        
    }
    
    @Test
    public void subExpressionTests() {
        var parser = new ParserExpr();
        assertEquals(6, parser.parse("3 - - 3").eval());
        assertEquals(0, parser.parse("3 +- 3").eval());
        
        assertEquals(9, parser.parse("-3 * - 3").eval());
        assertEquals(-1, parser.parse("3 / -3").eval());
        assertEquals(12, parser.parse("3.0*(-4.0*(2.0-3.0))").eval());
        assertEquals(-12, parser.parse("-3.0*(-4.0*(2.0-3.0))").eval());
        assertEquals(60, parser.parse("-(3.0*(-4.0*(2.0- -3.0)))").eval());
        
        assertEquals(-5, parser.parse("-(3+2)").eval());
    }
}
