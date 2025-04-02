package pt.isel.mpd.weather0.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastInfoDto {

    private List<WeatherInfoForecastDto> list;

    @SerializedName("city")
    private LocalDto local;

    public List<WeatherInfoForecastDto> getForecast() { return list; }
    public LocalDto getLocal() { return local; }
}
