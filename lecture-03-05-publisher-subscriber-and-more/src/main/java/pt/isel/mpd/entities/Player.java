package pt.isel.mpd.entities;

import org.json.JSONObject;

import java.io.Serializable;

public record Player(String name, Team.Position position, int age) {
    
    public JSONObject toJson() {
        var playerJson = new JSONObject();
        playerJson.put("name", name);
        playerJson.put("age", age);
        playerJson.put("position", position.name());
        return playerJson;
    }
    
    public static Player fromJson(JSONObject jsonPlayer)  {
        int age = jsonPlayer.getInt("age");
        String name = jsonPlayer.getString("name");
        Team.Position position = jsonPlayer.getEnum(Team.Position.class, "position");
        
        return new Player(name, position, age);
    }
    
    public static Player fromJson(String jsonText)   {
        return fromJson(new JSONObject(jsonText));
    }
    
}
