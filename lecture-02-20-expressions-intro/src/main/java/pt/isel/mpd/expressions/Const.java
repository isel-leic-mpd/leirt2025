package pt.isel.mpd.expressions;

public class Const implements Expr {
    public final double value;
    
    public Const(double value) {
        this.value = value;
    }
    
    @Override
    public double eval() {
        return value;
    }
    
    @Override
    public String getFormula() {
        return Double.valueOf(value).toString();
    }
}
