package pt.isel.mpd.products;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.products.visitors.ShowStoreVisitor;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class VisitorTests {
    
    @Test
    public void showElectronicsWithAVisitor() throws IOException {
        var packPromo = new Promo(StoreDB.packTvs, 25);
        
        try (Writer writer = new OutputStreamWriter(System.out)) {
            var visitor = new ShowStoreVisitor(writer);
            packPromo.accept(visitor);
        }
    }
    
}
