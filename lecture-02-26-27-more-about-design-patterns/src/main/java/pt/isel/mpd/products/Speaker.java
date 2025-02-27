package pt.isel.mpd.products;

import pt.isel.mpd.products.visitors.ElectronicsVisitor;

public class Speaker extends BaseElectronics {
    private final double power; // in Watts

    public Speaker(String name, String brand, double price, double power) {
        super(name, brand, price);
        this.power = power;
    }

    @Override
    public Category getCategory() {
        return Category.AUDIO;
    }

    public double getPower() {
        return power;
    }
    
    @Override
    public void accept(ElectronicsVisitor visitor) {
        visitor.visit(this);
    }

}
