package pt.isel.mpd.news.pub_sub;

public interface Publisher<T> {
    void subscribe(Subscriber<T> subscriber);
    void unSubscribe(Subscriber<T> subscriber);
    void next(T value);
    void end();
}
