package pt.isel.mpd.news;

import pt.isel.mpd.news.pub_sub.PublisherImpl;

public abstract class NewsEmitter extends PublisherImpl<String> {
    boolean terminated;
    public final String name;
    
    public NewsEmitter(String name) {
        this.name = name;
    }
    
    @Override
    public void next(String news) {
        if (!terminated) {
            String result = getType() + ':' + news;
            System.out.printf("Emitting '%s'\n", result);
            super.next(result);
        }
    }
    
    @Override
    public void end() {
        if (!terminated) {
            terminated = true;
            super.end();
        }
    }
    
    public abstract String getType();
}
