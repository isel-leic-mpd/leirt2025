package pt.isel.mpd.news.pub_sub;

import java.util.ArrayList;
import java.util.List;

public class PublisherImpl<T> implements Publisher<T>{
    
    private List<Subscriber<T>> subscribers = new ArrayList<>();
    @Override
    public void subscribe(Subscriber<T> subscriber) {
        subscribers.add(subscriber);
    }
    
    @Override
    public void unSubscribe(Subscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }
    
    
    @Override
    public void next(T value) {
        for(var s : subscribers) {
            s.onNext(this, value);
        }
    }
    
    @Override
    public void end() {
        for(var s : subscribers) {
            s.onEnd(this);
        }
    }
}
