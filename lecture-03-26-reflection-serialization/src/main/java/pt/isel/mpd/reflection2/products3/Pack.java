package pt.isel.mpd.reflection2.products3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Pack extends BaseElectronics implements Iterable<Electronics> {

	private final List<Electronics> products;


	// lazy version of Pack iterator
	private class PackIterator implements Iterator<Electronics> {

		private Stack<Iterator<Electronics>>
			iterStack = new Stack<>();
		private Iterator<Electronics> iterTop;
		private Electronics curr;

		private PackIterator() {
			iterTop = products.iterator();
			iterStack.push(iterTop);
		}

		@Override
		public boolean hasNext() {
			if (curr != null) return true;
			while(true) {
				while (!iterTop.hasNext()) {
					if (iterStack.empty()) return false;
					iterTop = iterStack.pop();
				}
				curr = iterTop.next();
				if (!(curr instanceof Pack pack)) {
					return true;
				}
				iterTop = pack.products.iterator();
				iterStack.push(iterTop);
			}
		}

		@Override
		public Electronics next() {
			if (!hasNext()) throw new NoSuchElementException();
			var n = curr;
			curr = null;
			return n;
		}
	}

	private void getAllIndividualProductsRec(
		Pack pack, List<Electronics> allProducts) {

		for (var p : pack.products) {
			if (p instanceof Pack pp)
				getAllIndividualProductsRec(pp, allProducts);
			else allProducts.add(p);
		}

	}

	private Iterable<Electronics> getAllIndividualProducts() {
		var allProduts = new ArrayList<Electronics>();
		getAllIndividualProductsRec(this, allProduts);
		return allProduts;
	}



	private static double productsPrice(List<Electronics> products) {
		double totalPrice = 0;
		for (var p : products) {
			totalPrice += p.getPrice();
		}
		return totalPrice;
	}

	public Pack(String name, List<Electronics> products) {
		super(name, "MultiBrand Pack", productsPrice(products));
		this.products = products;
	}

	@Override
	public Category getCategory() {
		return Category.MULTI;
	}

	@Override
	public Iterator<Electronics> iterator() {
		return getAllIndividualProducts().iterator();
	}


	public Iterator<Electronics> iterator0() {
		return new PackIterator();
	}

	@Override
	public JSONObject saveToJson() {
		JSONObject json = super.saveToJson();
		var jsonProducts = new JSONArray();
		for (var p : products) {
			jsonProducts.put(p.saveToJson());
		}
		json.put("products", jsonProducts);
		return json;
	}

	public static Pack fromJson(JSONObject json) {
		var name = json.getString("name");
		var products = new ArrayList<Electronics>();
		var jsonProds = json.getJSONArray("products");
		for(int i= 0; i < jsonProds.length(); ++i) {
			var jsonProd = jsonProds.getJSONObject(i);
			products.add(Electronics.createFrom(jsonProd));
		}
		return new Pack(name, products );
	}
}
