package pt.isel.mpd.reflection2.expressions;

public class Const implements Expr {
    public final double value;
    
    public Const() {
        value = 0;
    }
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
    
    @Override
    public String toString()  {
        return Double.toString(value);
    }
  
}
