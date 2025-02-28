package pt.isel.mpd.spreadsheet0.model;

import pt.isel.mpd.expressions.Const;
import pt.isel.mpd.expressions.Expr;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Spliterator;

public class Cell {
    public final CalcSheet sheet;
    public final int col, row;
    private Expr expr;
    private Optional<Double> value;
    
    public Cell(CalcSheet sheet, int row, int col) {
        this.sheet = sheet;
        this.col = col;
        this.row = row;
        value = Optional.empty();
        expr = new Const(0);
    }
    
    public Expr getExpr() {
        return expr;
    }
    
    public double eval() {
        return expr.eval();
//        value = value.or(() -> Optional.of(expr.eval()));
//        return value.get();
    }
    
    public String getFormula() {
        return expr.getFormula();
    }
    
    public void setValue(Expr expr) {
        this.expr = expr;
    }
}
