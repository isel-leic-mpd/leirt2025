package pt.isel.mpd.products;

public class TV extends BaseElectronics {
    private final Resolution res;    // in pixels
    private final double screenSize; // in inches

    public TV(String name, String brand, double price, Resolution res, double screenSize) {
        super(name, brand, price);
        this.res = res;
        this.screenSize = screenSize;
    }

    @Override
    public Category getCategory() {
        return Category.VIDEO;
    }
    
    public Resolution getResolution() {
        return res;
    }
    
    public double getScreenSize() {
        return screenSize;
    }
}
