package pt.isel.mpd.completable_futures_intro.weather4.exceptions;

public class WeatherApiException extends RuntimeException {
    public WeatherApiException(String msg) {
        super(msg);
    }
}
