package pt.isel.mpd.expressions;

public class Memo implements Expr {
    private final String formula;
    private final Expr expr;
    
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
}
