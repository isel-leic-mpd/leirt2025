package pt.isel.mpd.spreadsheet0.model;

import pt.isel.mpd.expressions.Expr;

// to complete
public class CellRef implements Expr {
    public final String name;
    private final CalcSheet sheet;
    
    public CellRef(String name, CalcSheet sheet) {
        this.name = name;
        this.sheet = sheet;
    }
    
    private Cell getCell() {
        var c = sheet.coordsFromName(name);
        return sheet.getCellAt(c.row(), c.col());
    }
    @Override
    public double eval() {
        return getCell().eval();
    }
    
    @Override
    public String getFormula() {
       return getCell().getFormula();
    }
}
