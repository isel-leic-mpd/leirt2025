import org.junit.jupiter.api.Test;
import pt.isel.mpd.news.NewsSubscriber;
import pt.isel.mpd.news.PoliticsEmitter;
import pt.isel.mpd.news.SportsEmitter;
import pt.isel.mpd.news.pub_sub.Subscriber;

public class PubSubTests {
    @Test
    public void pubSubManyToManyTests() {
        var sports = new SportsEmitter("sports feed");
        var politics = new PoliticsEmitter("politics feed");
        
        var all_reader = new NewsSubscriber("all");
        var sports_reader = new NewsSubscriber("sports");
        var politics_reader = new NewsSubscriber("politics");
        
        all_reader.start(sports, politics);
        sports_reader.start(sports);
        politics_reader.start(politics);
        
        sports.next("Benfica champion 2024/2025");
        politics.next("Trump buys Greenland");
        
        all_reader.stop(sports);
        sports.next("Gyokeres sold to Manchester United");
    }
}
