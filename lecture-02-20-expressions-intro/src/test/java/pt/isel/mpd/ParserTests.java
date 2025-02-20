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
    
   
}
