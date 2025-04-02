package pt.isel.mpd.weather0.exceptions;

public class WeatherApiException extends RuntimeException {
    public WeatherApiException(String msg) {
        super(msg);
    }
}
