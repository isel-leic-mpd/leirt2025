package pt.isel.mpd.expressions;

import pt.isel.mpd.exceptions.DivByZeroException;

public class Div extends BinExpr {
    
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
