package pt.isel.mpd.products;

import pt.isel.mpd.products.visitors.ElectronicsVisitor;

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
    
    @Override
    public void accept(ElectronicsVisitor visitor) {
        visitor.visit(this);
    }
    
    public Resolution getResolution() {
        return res;
    }
    
    public double getScreenSize() {
        return screenSize;
    }
}
