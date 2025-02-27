package pt.isel.mpd.spreadsheet0.parsers;

import pt.isel.mpd.parser.Lex;
import pt.isel.mpd.parser.ParserExpr;
import pt.isel.mpd.spreadsheet0.model.CalcSheet;
import pt.isel.mpd.exceptions.ParserException;
import pt.isel.mpd.expressions.Const;
import pt.isel.mpd.expressions.Expr;


import pt.isel.mpd.spreadsheet0.model.CellRef;

public class ParserExpr2 extends ParserExpr {
    private static final String MAX = "MAX";
    private static final String SUM = "SUM";
    
    private final CalcSheet sheet;
    private String startRange, endRange;
    
    public ParserExpr2(CalcSheet sheet) {
        this.sheet = sheet;
    }
    
    
    protected Expr factor() throws ParserException {
        if (token.isWord()) {
            String name = token.getWord();
            nextToken();
            return new CellRef(name, sheet);
        }
        if (token.isNumber())  {
            double number = token.getNumber();
            nextToken();
            return new Const(number);
        }
        else if (token.getType() == Lex.TokType.OPEN_BRACKET) {
            nextToken();
            Expr expr = expression();
            if (token.getType() == Lex.TokType.CLOSE_BRACKET) {
                nextToken();
                return expr;
            }
        }
        throw new ParserException("Number, Word or parentheses expected!");
    }
}
