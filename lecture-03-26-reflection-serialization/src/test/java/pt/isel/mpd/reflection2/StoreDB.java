package pt.isel.mpd.reflection2;

import pt.isel.mpd.reflection2.products3.*;

import java.util.List;

public class StoreDB {
    
    public final static Resolution hd = new Resolution(1024, 768);
    public final static Resolution fullHd = new Resolution(1920, 1080);
    public final static Resolution uhd = new Resolution(3840, 2160);
    
    // TVs
    public final static TV sonyX95 = new TV("X95", "Sony", 3000, uhd, 65.0);
    public final static TV samsungU7 = new TV("u7", "Samsung", 2000, uhd, 60);
    
    // Speakers
    public final static Speaker jblX300 = new Speaker("x300", "JBL", 100, 40);
    public final static Speaker samsungS250 = new Speaker("s250", "Samsung", 200, 60);
    public final static Speaker samsungS300 = new Speaker("s300", "Samsung", 300, 100);
    
    // Promo
    public final static Promo sonyPromo = new Promo(sonyX95, 20);
    
    // Packs
    public final static Pack packSamsung = new Pack("SamsungBrand", List.of(samsungS300,samsungS250 ));
    public final static Pack packTvs = new Pack("TVS", List.of(samsungU7, sonyX95 ));
    public final static Pack superPack = new Pack("All in one", List.of(packTvs, packSamsung));
    
}
