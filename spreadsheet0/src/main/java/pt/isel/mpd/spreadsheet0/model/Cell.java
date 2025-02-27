package pt.isel.mpd.spreadsheet0.model;

import pt.isel.mpd.expressions.Const;
import pt.isel.mpd.expressions.Expr;

public class Cell {
    public final CalcSheet sheet;
    public final int col, row;
    private Expr expr;
    
    public Cell(CalcSheet sheet, int row, int col) {
        this.sheet = sheet;
        this.col = col;
        this.row = row;
        expr = new Const(0);
    }
    
    public Expr getExpr() {
        return expr;
    }
    
    public double eval() {
        return expr.eval();
    }
    public String getFormula() {
        return expr.getFormula();
    }
    
    public void setValue(Expr expr) {
        this.expr = expr;
    }
}
