package pt.isel.mpd.products2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class StoreTests {
    private final static Store store = new Store();
    
    // Store catalog
    static {
        store.addCatalog(StoreDB.packSamsung);
        store.addCatalog(StoreDB.sonyPromo);
    }
    
    @Test
    public void productsFromSamsungTests() {
      var json1 = store.saveToJson();
      System.out.println(json1.toString());
      
      var store2 = Store.fromJson(json1);
      
      var json2 = store2.saveToJson();
      
      assertEquals(json1.toString(), json2.toString());
     
    }
}
