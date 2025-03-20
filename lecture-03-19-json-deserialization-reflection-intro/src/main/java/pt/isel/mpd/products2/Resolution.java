package pt.isel.mpd.products2;

import org.json.JSONObject;

public class Resolution {
    public final int height; // pixels height
    public final int width;  // pixels width

    public Resolution(int width, int height) {
        this.width = width; this.height = height;
    }

    public JSONObject saveToJson( ) {
        JSONObject json = new JSONObject();
        json.put("width", width);
        json.put("height", height);
        return json;
    }
    
    public static Resolution fromJson(JSONObject json) {
        return new Resolution(json.getInt("width"), json.getInt("height"));
    }
    
 
}
