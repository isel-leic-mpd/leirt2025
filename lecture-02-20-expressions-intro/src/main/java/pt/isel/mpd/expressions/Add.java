package pt.isel.mpd.expressions;



public class Add extends BinExpr {
    
    public Add(Expr left, Expr right) {
        super(left, right);
    }
    
    @Override
    public String getOperator() {
        return "+";
    }
    
    @Override
    public double eval() {
        return left.eval() + right.eval();
    }
  
}
