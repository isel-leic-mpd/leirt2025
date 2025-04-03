package pt.isel.mpd.weather0;

import com.google.gson.Gson;
import pt.isel.mpd.weather0.dto.*;
import pt.isel.mpd.weather0.exceptions.WeatherApiException;
import pt.isel.mpd.weather0.utils.TimeUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OpenWeatherWebApi {
    private static final String API_KEY;

    private static final String WEATHER_HOST =
            "http://api.openweathermap.org/";

    private static final String GEO_SERVICE =
            "geo/1.0/";

    private static final String WEATHER_SERVICE =
            "http://api.openweathermap.org/data/2.5/";

    private static final String WEATHER_AT_TEMPLATE =
            "weather?lat=%f&lon=%f&units=metric&appid=%s";
    
    private static final String FORECAST_WEATHER_TEMPLATE =
            "forecast?lat=%f&lon=%f&units=metric&appid=%s";

    private static final String AIR_POLLUTION_AT_TEMPLATE =
            "air_pollution?lat=%f&lon=%f&appid=%s";

    private static final String AIR_POLLUTION_HISTORY_TEMPLATE =
            "air_pollution/history?lat=%f&lon=%f&start=%d&end=%d&appid=%s";

    private static final String LOCATION_SEARCH_TEMPLATE =
        WEATHER_HOST +
        GEO_SERVICE +
        "direct?q=%s&limit=10&lang=pt&appid=%s";

    protected final Gson gson;
    
    
    /**
     * Retrieve API-KEY from resources
     * @return
     */
    private static String getApiKeyFromResources() {
        try {
            URL keyFile =
                    ClassLoader.getSystemResource("openweatherapi-app-key.txt");
            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }

        }
        catch(IOException e) {
            throw new IllegalStateException(
                    "YOU MUST GET a KEY from  openweatherapi.com and place it in src/main/resources/openweatherapi-app-key2.txt");
        }
    }
    
    /**
     * Static Constructor
     */
    static {
        API_KEY = getApiKeyFromResources();
    }

    private Reader httpGet(String path) {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
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
    
    // API METHODS
    
    /**
     * Get WeatherInfo's from a local coordinates given a date interval
     * @param lat
     * @param lon
     * @return
     */
    public WeatherInfoDto weatherAt(double lat, double lon) {
        String path =  WEATHER_SERVICE +
            String.format(WEATHER_AT_TEMPLATE, lat, lon, API_KEY);
        var reader = httpGet(path);
        return gson.fromJson(reader, WeatherInfoDto.class);
    }

    /**
     * Get current air pollution metrics from a local coordinates
     * @param lat
     * @param lon
     * @return
     */
    public PollutionInfoDto airPollutionAt(double lat, double lon) {
        String path =   WEATHER_SERVICE +
            String.format(AIR_POLLUTION_AT_TEMPLATE, lat, lon, API_KEY);
        PollutionInfoQueryDto pi =
            gson.fromJson(httpGet(path), PollutionInfoQueryDto.class);
        if (pi.list == null || pi.list.length != 1)
            throw new WeatherApiException("response list must have one element");
        return pi.list[0];
    }

    /**
     * Get WeatherInfo's forecast for a local coordinates
     * @param lat
     * @param lon
     * @return
     */
    public List<WeatherInfoForecastDto> forecastWeatherAt(double lat, double lon) {
        String path =  WEATHER_SERVICE + String.format(FORECAST_WEATHER_TEMPLATE, lat, lon, API_KEY);
        
        ForecastInfoDto finfo =
                gson.fromJson(httpGet(path), ForecastInfoDto.class);
        System.out.println(finfo.getLocal());
        return finfo.getForecast();
    }

   
    /**
     * Get local info given the name of the local
     * @param location
     * @return
     */
    public List<LocationDto> search(String location) {
        String path =  String.format(LOCATION_SEARCH_TEMPLATE, location, API_KEY);
        LocationDto[] search = gson.fromJson(httpGet(path), LocationDto[].class);
        return Arrays.asList(search);
    }

    public List<PollutionInfoDto> pollutionHistoryAt(
            double lati, double longi, LocalDate start, LocalDate end) {

        String path =  WEATHER_SERVICE +
            String.format(AIR_POLLUTION_HISTORY_TEMPLATE,
                            lati, longi,
                    TimeUtils.toUnixTime(start), TimeUtils.toUnixTime(end), API_KEY);
        
        PollutionInfoQueryDto winfo =
                gson.fromJson(httpGet(path), PollutionInfoQueryDto.class);
        return Arrays.asList(winfo.list);
    }
    
    public OpenWeatherWebApi() {
        gson = new Gson();
    }
}
