import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import pt.isel.mpd.entities.Player;
import pt.isel.mpd.entities.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTests {
    
    @Test
    public void simpleJsonTest() {
        var jsonObj = new JSONObject();
        jsonObj.put("name", "Pavlidis");
        jsonObj.put("age", 26);
        jsonObj.put("position", Team.Position.Forward.name());
        System.out.println(jsonObj.toString(4));
    }

    @Test
    public void playerToJsonConversionTest() {
        var p = new Player("Vangelis Pavlidis", Team.Position.Forward, 26 );
        System.out.println(p.toJson().toString(2));
    }
    
    @Test
    public void jsonToPlayerConversionTest() {
        var jsonText = """
                    {
                        "name":"Vangelis Pavlidis",
                        "position":"Forward",
                        "age" : "26"
                     }
                       """;
        var p = Player.fromJson(new JSONObject(jsonText));
        System.out.println(p);
    }
    
    @Test
    public void teamToJsonConversionTest() {
        var rafaSilva = new Player("Vangelis Pavlidis", Team.Position.Forward, 26 );
        var diMaria = new Player("Angel di Maria", Team.Position.Forward, 37 );
        var aSilva = new Player("Antonio Silva", Team.Position.CenterBack, 22 );
        var team  = new Team("Benfica", List.of(aSilva, rafaSilva, diMaria));
        var teamText = team.toJson().toString(2);
        var team2 = Team.fromJsonText(teamText);
        assertEquals(team, team2);
        System.out.println(teamText);
    }
    
    @Test
    public void jsonToTeamConversionTest() {
        var jsonText = """
             {
                "players": [
                    {
                        "name": "Antonio Silva",
                        "position": "CenterBack",
                        "age": 20
                    },
                    {
                        "name": "Vangelis Pavlidis",
                        "position": "Forward",
                        "age": 26
                    },
                    {
                        "name": "Angel di Maria",
                        "position": "Forward",
                        "age": 37
                    }
                ],
                "name": "Benfica"
              }
            """;
        var t = Team.fromJson(new JSONObject(jsonText));
       
        var json2 = t.toJson().toString(4);
        System.out.println(json2);
        json2 = json2.replaceAll("\\s", "");
        jsonText = jsonText.replaceAll("\\s", "");
        assertEquals(jsonText, json2);
       
    }
}
