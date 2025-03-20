package pt.isel.mpd.products2;

import org.json.JSONObject;

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
    public JSONObject saveToJson() {
        JSONObject json = super.saveToJson();
        json.put("power", power);
        return json;
    }

    public static Speaker fromJson(JSONObject json) {
        var name = json.getString("name");
        var price = json.getDouble("price");
        var power = json.getDouble("power");
        var brand = json.getString("brand");
        return new Speaker(name, brand, price, power);
    }

}
