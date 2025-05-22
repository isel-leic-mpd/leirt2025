package pt.isel.mpd.completable_futures_intro.weather4.requests;
import java.io.Reader;
import java.util.concurrent.CompletableFuture;

public interface AsyncRequest {
    CompletableFuture<Reader> getAsync(String path);
}