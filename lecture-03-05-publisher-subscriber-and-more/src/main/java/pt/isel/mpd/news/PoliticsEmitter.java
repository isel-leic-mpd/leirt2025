package pt.isel.mpd.news;

public class PoliticsEmitter extends NewsEmitter {
    
    public PoliticsEmitter(String name) {
        super(name);
        
    }
    @Override
    public String getType() {
        return "Politics";
    }
}
