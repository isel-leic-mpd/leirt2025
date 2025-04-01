package pt.isel.mpd.reflection2.expressions;

import pt.isel.mpd.reflection2.expressions.exceptions.DivByZeroException;


public class Div extends BinExpr {
    
    public Div() {
        super(null, null);
    }
    
    public Div(Expr left, Expr right) {
        super(left, right);
    }
    
    @Override
    public String getOperator() {
        return "/";
    }
    
    @Override
    public double eval() {
        var r = right.eval();
        if (r == 0) {
            throw new DivByZeroException();
        }
        return left.eval() / r;
    }
    
 
}
