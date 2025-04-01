package pt.isel.mpd.reflection2.expressions;



public class Mul extends BinExpr {
    public Mul() {
        super(null, null);
    }
    
    public Mul(Expr left, Expr right) {
        super(left, right);
    }
    
    @Override
    public String getOperator() {
        return "*";
    }
    
    @Override
    public double eval() {
        return left.eval()*right.eval();
    }
    
}
