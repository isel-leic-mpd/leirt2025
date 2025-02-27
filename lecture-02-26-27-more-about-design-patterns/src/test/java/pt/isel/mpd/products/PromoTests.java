package pt.isel.mpd.products;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.products.Promo;
import pt.isel.mpd.products.TV;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromoTests {
    
    @Test
    public void checkCorrectPromoAttributesTest() {
    
        var promoSony = new Promo(StoreDB.sonyX95, 20);
        var expectedPrice = 2400;
        var expectedName = "Promo " + StoreDB.sonyX95.getName();
        var expectedCategory = StoreDB.sonyX95.getCategory();


        assertEquals(expectedName, promoSony.getName());
        assertEquals(expectedCategory, promoSony.getCategory());
        assertEquals(expectedPrice, promoSony.getPrice());
        assertEquals(20, promoSony.getDiscount());
       
    }
}
