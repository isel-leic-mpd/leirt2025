package pt.isel.mpd.reflection2.expressions;


public class Sub extends BinExpr {
    public Sub() {
        super(null, null);
    }
    
    public Sub(Expr left, Expr right) {
        super(left, right);
    }
    
    @Override
    public String getOperator() {
        return "-";
    }
    
    @Override
    public double eval() {
        return left.eval() - right.eval();
    }
    
    
}