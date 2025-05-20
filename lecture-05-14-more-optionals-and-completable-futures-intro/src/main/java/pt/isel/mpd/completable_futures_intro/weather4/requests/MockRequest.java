package pt.isel.mpd.completable_futures_intro.weather4.requests;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class MockRequest implements Request{

	private static String CACHE_NAME = "weather_cache";

	private static final String CACHE_PATH = getCachePath();


	private static String getCachePath() {
		URL url = MockRequest.class.getClassLoader().getResource(CACHE_NAME);
		try {
			File file = new File(url.toURI());
			return file.getAbsolutePath() + "\\";
		}
		catch(URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static String convert(String path) {
		return path.substring(path.lastIndexOf('/')+1, path.lastIndexOf('&'))
				   .replace('&', '-')
				   .replace( '?','-')
				   .replace( ',','-')+ ".txt";
	}

	@Override
	public Reader get(String path) {
		path =  convert(path);
		try {
			return new InputStreamReader(
				ClassLoader.getSystemResource(CACHE_NAME + "/" + path).openStream());
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void saveOn(String path, Reader reader) {
		path =  convert(path);
		String absPath = CACHE_PATH + path;
		try (PrintWriter writer = new PrintWriter(absPath)) {
			reader.transferTo(writer);
		}
		catch (IOException e){
			throw new UncheckedIOException(e);
		}
	}
}
