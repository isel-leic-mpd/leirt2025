package pt.isel.mpd.parser;



public class ParserExpr {
    /*
    protected final Lex lex;
    protected Lex.Token token;
    
    
    public ParserExpr() {
        this.lex = new Lex();
    }
    
    protected void nextToken()  {
        token = lex.next();
    }
    
    protected Expr factor() throws ParserException {
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
        throw new ParserException("Number or parentheses expected!");
    }
    
    private Expr term( ) throws ParserException {
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
    
    public Expr expression() throws ParserException {
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
    
    public Expr parse(String line) throws ParserException {
        lex.start(line);
        nextToken();
        Expr expr =  expression();
        if (token.getType() != Lex.TokType.END)
            throw new ParserException("End of expression expected!");
        return expr;
    }
    
     */
    
}
