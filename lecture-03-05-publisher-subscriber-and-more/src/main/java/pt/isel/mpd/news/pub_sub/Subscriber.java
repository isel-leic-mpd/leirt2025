package pt.isel.mpd.news.pub_sub;

public interface Subscriber<T> {
    void onNext(Publisher<T> src, T value);
    void onEnd(Publisher<T> src);
}
