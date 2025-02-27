package pt.isel.mpd.products;

import pt.isel.mpd.products.visitors.ElectronicsVisitor;

public class Promo extends BaseElectronics {
    private Electronics original;
	private int discount;
	
	private static double newPrice(Electronics orig, int discount) {
		return orig.getPrice() * (100-discount) / 100;
	}
	public Promo(Electronics orig, int discount) {
		super("Promo " + orig.getName(),
			  orig.getBrand(), newPrice(orig, discount));
		this.original = orig;
		this.discount = discount;
	}
	
	@Override
	public Category getCategory() {
		return original.getCategory();
	}
	
	public int getDiscount() {
		return discount;
	}
	
	public Electronics getOriginal() {
		return original;
	}
	@Override
	public void accept(ElectronicsVisitor visitor) {
		visitor.visit(this);
	}
}
