package pt.isel.mpd.completable_futures_intro.weather4.requests;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class HttpRequest implements  Request {

	@Override
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
