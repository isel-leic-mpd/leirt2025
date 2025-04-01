package pt.isel.mpd.reflection2.products3;

import org.json.JSONObject;

public class TV extends BaseElectronics  {
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
    public JSONObject saveToJson() {
        JSONObject json = super.saveToJson();
        json.put("resolution", res.saveToJson());
        json.put("screenSize",screenSize);
        return json;
    }
    
    
    public Resolution getResolution() {
        return res;
    }

   
    public double getScreenSize() {
        return screenSize;
    }
    
    public static TV fromJson(JSONObject json) {
        var price = json.getDouble("price");
        var name = json.getString("name");
        var brand = json.getString("brand");
        var size = json.getDouble("screenSize");
        var res = Resolution.fromJson(json.getJSONObject("resolution"));
        
        return new TV(name, brand, price, res, size);
    }
    
}
