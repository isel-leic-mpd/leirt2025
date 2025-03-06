package pt.isel.mpd.entities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public record Team(String name, List<Player> players) {
    public enum Position {
        GoalKeeper, CenterBack, WingBack, MidFielder, Forward
    }
    
    
    public JSONObject toJson() {
        var teamJson = new JSONObject();
        teamJson.put("name", name);
        var playersJson = new JSONArray();
        
        var i = 0;
        for (var p : players) {
            playersJson.put(i++, p.toJson());
        }
        teamJson.put("players", playersJson);
        return teamJson;
    }
    
    public static Team fromJson(JSONObject jsonTeam) {
        String name = jsonTeam.getString("name");
        var jsonPlayers = jsonTeam.getJSONArray("players");
        var playersList = new ArrayList<Player>();
        for (int i = 0; i < jsonPlayers.length(); ++i) {
            playersList.add(Player.fromJson(jsonPlayers.getJSONObject(i)));
        }
        return new Team(name, playersList);
    }
    
    public static Team fromJsonText(String jsonText) {
        return fromJson(new JSONObject(jsonText));
    }
}
    
    

