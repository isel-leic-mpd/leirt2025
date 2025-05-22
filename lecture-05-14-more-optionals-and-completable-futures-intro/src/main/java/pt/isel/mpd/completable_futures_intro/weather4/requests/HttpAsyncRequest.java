package pt.isel.mpd.completable_futures_intro.weather4.requests;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HttpAsyncRequest implements AsyncRequest {

    @Override
    public CompletableFuture<Reader> getAsync(String path)  {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
            .uri(URI.create(path))
            .GET()
            .build();
        
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                   .thenApply(resp -> new InputStreamReader(resp.body()));
        
    }
}