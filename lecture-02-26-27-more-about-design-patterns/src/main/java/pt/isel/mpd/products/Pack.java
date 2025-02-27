package pt.isel.mpd.products;

import pt.isel.mpd.products.visitors.ElectronicsVisitor;

import java.util.*;

public class Pack extends BaseElectronics implements Iterable<Electronics> {

	private final List<Electronics> products;
	
	private static double productsPrice(List<Electronics> products) {
		double totalPrice = 0;
		for (var p : products) {
			totalPrice += p.getPrice();
		}
		return totalPrice;
	}

	public Pack(String name, List<Electronics> products) {
		super(name, "MultiBrand Pack", productsPrice(products));
		this.products = new ArrayList<>(products);
	}

	@Override
	public Category getCategory() {
		return Category.MULTI;
	}

	@Override
	public Iterator<Electronics> iterator() {
		return products.iterator();
	}
	@Override
	public void accept(ElectronicsVisitor visitor) {
		visitor.visit(this);
	}
}
