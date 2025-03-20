package pt.isel.mpd.products2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Electronics> catalog = new ArrayList<>();

    public Store addCatalog(Electronics product) {
        catalog.add(product);
        return this;
    }

    public JSONObject saveToJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonCatalog = new JSONArray();
        for (var p : catalog) {
            jsonCatalog.put(p.saveToJson());
        }
        json.put("items", jsonCatalog);
        return json;
    }

    public static Store fromJson(JSONObject json) {
        var store = new Store();
        var jsonCatalog = json.getJSONArray("items");
        
        for(int i=0; i < jsonCatalog.length(); ++i) {
            store.addCatalog(Electronics.createFrom(jsonCatalog.getJSONObject(i)));
        }
        return store;
    }
}
