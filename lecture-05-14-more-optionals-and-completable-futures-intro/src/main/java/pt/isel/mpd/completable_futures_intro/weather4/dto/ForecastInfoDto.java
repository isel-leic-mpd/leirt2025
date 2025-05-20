package pt.isel.mpd.completable_futures_intro.weather4.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastInfoDto {

    private List<WeatherInfoForecastDto> list;

    @SerializedName("city")
    private LocalDto local;

    public List<WeatherInfoForecastDto> getForecast() { return list; }
    public LocalDto getLocal() { return local; }
}
