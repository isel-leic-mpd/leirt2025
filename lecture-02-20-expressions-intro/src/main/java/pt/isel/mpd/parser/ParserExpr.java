package pt.isel.mpd.parser;


import pt.isel.mpd.exceptions.ParserException;
import pt.isel.mpd.expressions.*;

public class ParserExpr {
    protected final Lex lex;
    protected Lex.Token token;
    
    
    public ParserExpr() {
        this.lex = new Lex();
    }
    
    protected void nextToken()  {
        token = lex.next();
    }
    
    protected Expr factor() {
        boolean negative = false;
        Expr expr = null;
        if (token.getType() == Lex.TokType.OP_SUB) {
            nextToken();
            negative = true;
        }
        if (token.isNumber()) {
            double number = token.getNumber();
            nextToken();
            expr = new Const(number);
        } else if (token.getType() == Lex.TokType.OPEN_BRACKET) {
            nextToken();
            expr = expression();
            if (token.getType() != Lex.TokType.CLOSE_BRACKET) {
                throw new ParserException("close parenthesis expected!");
            }
            nextToken();
        }
        if (expr == null) {
            throw new ParserException("Number or parentheses expected!");
        }
        if (negative) {
            if (expr instanceof Const e) {
                expr = new Const(-e.value);
            } else {
                expr = new Mul(new Const(-1), expr);
            }
        }
        return expr;
        
    }
    
    private Expr term( )   {
        Expr expr = factor();
        while ((token.getType() == Lex.TokType.OP_MUL || token.getType() == Lex.TokType.OP_DIV)) {
            Lex.TokType type =  token.getType();
            nextToken();
            Expr right = factor();
            
            if (type == Lex.TokType.OP_MUL) {
                expr  = new Mul(expr, right);
            } else {
                expr = new Div(expr, right);
            }
            
        }
        return expr;
    }
    
    
    
    public Expr expression() {
        Expr expr = term();
        while ((token.getType() == Lex.TokType.OP_ADD ||
                    token.getType() == Lex.TokType.OP_SUB)) {
            Lex.TokType type =  token.getType();
            nextToken();
            Expr right = term();
            if (type == Lex.TokType.OP_ADD) {
                expr = new Add(expr, right);
            } else {
                expr = new Sub(expr, right);
            }
        }
        return expr;
    }
    
    public Expr parse(String line)  {
        lex.start(line);
        nextToken();
        Expr expr =  expression();
        if (token.getType() != Lex.TokType.END)
            throw new ParserException("End of expression expected!");
        return expr;
    }
    
}
