package pt.isel.mpd.weather2.requests;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class HttpRequest implements Request {
    public Reader get(String path)  {
        HttpClient client = HttpClient.newHttpClient();
        var request = java.net.http.HttpRequest.newBuilder()
                                               .uri(URI.create(path))
                                               .GET()
                                               .build();
        try {
            InputStream input =  client
                                 .send(request, HttpResponse.BodyHandlers.ofInputStream())
                                 .body();
            return new InputStreamReader(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
