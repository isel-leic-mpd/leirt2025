package pt.isel.mpd;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.expressions.Const;
import pt.isel.mpd.spreadsheet0.model.CalcSheet;
import pt.isel.mpd.spreadsheet0.parsers.ParserExpr2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalcSheetTests {
    @Test
    public void allCellsAreZeroOnCreationTest() {
        int rows = 10; int cols = 20;
        var sheet = new CalcSheet(rows,cols);
        
        for(int row = 0; row < rows; row++) {
            for(int col=0; col < cols; col++) {
                var e = sheet.getCellAt(row,col);
                assertTrue(e.getExpr() instanceof Const);
                assertEquals(0, e.getExpr().eval());
            }
        }
    }
    
    @Test
    public void checkIfExpressionsWithCellRefProduceCorrectResults() {
        int rows = 10; int cols = 20;
        var sheet = new CalcSheet(rows,cols);
        var parser = new ParserExpr2(sheet);
        
        var exprA1 = parser.parse("3*(2+4)");
        var exprB1 = parser.parse("A1 + 5");
        
        sheet.setCellAt(0,0, exprA1);
        sheet.setCellAt(0, 1, exprB1);
        
        assertEquals(18, sheet.getExprAt(0,0).eval());
        assertEquals(23, sheet.getExprAt(0,1).eval());
        
        assertEquals("A1 + 5", sheet.getFormulaAt(0,1));
        assertEquals("3*(2+4)", sheet.getFormulaAt(0,0));
       
    }
    
  
    
    
}
