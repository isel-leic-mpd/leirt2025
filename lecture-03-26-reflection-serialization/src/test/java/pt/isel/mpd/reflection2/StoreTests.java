package pt.isel.mpd.reflection2;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.reflection2.products3.Store;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StoreTests {
    private final static Store store = new Store();
    
    // Store catalog
    static {
        store.addCatalog(StoreDB.superPack);
        store.addCatalog(StoreDB.sonyPromo);
    }
    
    @Test
    public void productsFromSamsungTests() {
      var json1 = store.saveToJson();
      System.out.println(json1.toString(4));
      
      var newStore = Store.fromJson(json1);
      var json2 = newStore.saveToJson();
      assertEquals(json1.toString(), json2.toString());
    }
}
