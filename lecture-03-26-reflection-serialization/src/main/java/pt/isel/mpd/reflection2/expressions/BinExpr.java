package pt.isel.mpd.reflection2.expressions;


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
        // parenthesis could be added but in this way we have
        // lots of stupid parenthesis that aren't needed
        //        return "(" + left.getFormula() + ")" +
        //                   getOperator() +
        //               "(" + right.getFormula() + ")";
        return left.getFormula() +
                   getOperator() +
               right.getFormula();
    }
    
    public abstract String getOperator();
    
    @Override
    public String toString()  {
        return getFormula();
    }
}
