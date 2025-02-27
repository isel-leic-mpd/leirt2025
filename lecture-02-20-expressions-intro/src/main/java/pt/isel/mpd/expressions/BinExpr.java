package pt.isel.mpd.expressions;


public abstract class BinExpr implements Expr {
    public final Expr left, right;
    
    protected BinExpr(Expr left, Expr right) {
        this.left = left; this.right = right;
    }
    
    /**
     * Returns the formula of the expression. using
     * the Template Method Pattern, where the getOperator method,
     * override by subclasses, is used to get the operator.
     * @return
     */
    public final String getFormula() {
        return "(" + left.getFormula() + ")" +
                   getOperator() +
               "(" + right.getFormula() + ")";
    }
    
    public abstract String getOperator();
    
}
