package pt.isel.mpd.spreadsheet0.model;

import pt.isel.mpd.expressions.Expr;
import pt.isel.mpd.spreadsheet0.exceptions.InvalidCoordinatesException;

public class CalcSheet {
    private final Cell[][] cells;
    private final int rows, cols;
    
    public  record  CellCoords(int row, int col){ }
    
    boolean validCoords(int row, int col) {
        return row>= 0 && row < rows && col >= 0 && col < cols;
    }
    
    /**
     * permite obter as coordenadas (row,col) representadas pelo nome de um CellRef
     * @param name
     * @return
     */
    public CellCoords coordsFromName(String name) {
        int col=-1, row=-1;
        try {
            name = name.toUpperCase();
            col = name.charAt(0) - 'A';
            if ( col  < 0 || col > cols) col = -1;
            row = Integer.parseInt(name.substring(1))-1;
            if (row < 0 || row > rows) row = -1;
        }
        catch(NumberFormatException ignored) { }
        if (col == -1 || row == -1 || !validCoords(row,col)) throw new InvalidCoordinatesException();
        return new CellCoords(row,col);
    }
    
    public CalcSheet(int rows, int cols) {
        this.cells = new Cell[rows][cols];
        this.rows = rows; this.cols = cols;
    }
    
    public Cell getCellAt(int row, int col) {
       if (!validCoords(row,col)) {
           throw new InvalidCoordinatesException();
       }
       return cells[row][col];
    }
    
    public Expr getExprAt(int row, int col) {
        return getCellAt(row,col).getExpr();
    }
    
    public String getFormulaAt(int row, int col) {
        return getCellAt(row,col).getFormula();
    }
    
    public void setCellAt(int row, int col, Expr expr)  {
        if (!validCoords(row,col)) {
            throw new InvalidCoordinatesException();
        }
        cells[row][col].setValue(expr);
    }
}
