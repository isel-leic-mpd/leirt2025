package pt.isel.mpd.expressions;

import pt.isel.mpd.exceptions.ParserException;

public class Factorial implements Expr {
    public final Expr expr;
    
    public Factorial(Expr expr) {
       this.expr = expr;
    }
    
    private double fact(double d) {
        if ((int) d !=d)  {
            throw new ParserException("fact operand must be an integer");
        }
        int f = 1;
        double df = (int) d;
        while(df > 1) {
            f*= df;
            df--;
        }
        return f;
    }
    @Override
    public double eval() {
        return fact(expr.eval());
    }
    
    @Override
    public String getFormula() {
        return "!${expr.getFormula}";
    }
}
