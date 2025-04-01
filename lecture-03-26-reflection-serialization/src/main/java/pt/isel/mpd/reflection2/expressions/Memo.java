package pt.isel.mpd.reflection2.expressions;



public class Memo implements Expr {
    private final String formula;
    private final Expr expr;
    
    public Memo() {
        formula = null;
        expr = null;
    }
    public Memo(Expr expr, String formula) {
        this.expr = expr;
        this.formula = formula;
    }
    
    @Override
    public double eval() {
        return expr.eval();
    }
    
    @Override
    public String getFormula() {
        return formula;
    }
    
    @Override
    public String toString()  {
        return expr.getFormula();
    }
}
