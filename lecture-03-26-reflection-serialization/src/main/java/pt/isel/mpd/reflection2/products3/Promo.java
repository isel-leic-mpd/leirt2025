package pt.isel.mpd.reflection2.products3;

import org.json.JSONObject;

public class Promo extends BaseElectronics {
	private final Electronics wrapped;
	private final int discount;
	
	private static double newPrice(double origPrice, int discount) {
		return (origPrice* (100 - discount))/100;
	}
	
	public Promo(Electronics wrappee, int discount) {
		super("Promo " + wrappee.getName(), wrappee.getBrand(),
			newPrice(wrappee.getPrice(), discount));
		if (discount <= 0 || discount > 100)
			throw new IllegalArgumentException("bad discount");
		this.wrapped = wrappee;
		this.discount = discount;
	}
	
	public Electronics getWrapee() {
		return wrapped;
	}
	
	public int getDiscount() {
		return discount;
	}
	
	@Override
	public Category getCategory() {
		return getWrapee().getCategory();
	}

	@Override
	public JSONObject saveToJson() {
		JSONObject json = super.saveToJson();
		json.put("wrapped", wrapped.saveToJson());
		json.put("discount", discount);
		return json;
	}
	
	public static Promo fromJson(JSONObject json) {
		var wrapped = Electronics.createFrom(json.getJSONObject("wrapped"));
		var discount = json.getInt("discount");
		return new Promo(wrapped, discount);
	}
}
