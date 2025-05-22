package pt.isel.mpd.completable_futures_intro.weather4;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.completable_futures_intro.weather4.dto.WeatherInfoDto;

public class OpenWeatherWebApiAsyncTests {
    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;
    
    @Test
    public void get_weather_at_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        WeatherInfoDto winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );
        System.out.println(winfo);
    }
}
