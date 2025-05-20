package pt.isel.mpd.completable_futures_intro.weather4;

import com.google.gson.Gson;

import pt.isel.mpd.completable_futures_intro.weather4.dto.WeatherInfoDto;
import pt.isel.mpd.completable_futures_intro.weather4.requests.AsyncRequest;
import pt.isel.mpd.completable_futures_intro.weather4.requests.HttpAsyncRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

// needs refactoring
class OpenWeatherWebApiAsync {
    private static final String API_KEY = getApiKeyFromResources();;
    
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
    
    public static String WEATHER_SCHEMA = "http://";
    
    public static final String WEATHER_HOST =
        "api.openweathermap.org";
    
    
    public static final String GEO_SERVICE =
        "/geo/1.0/";
    
    public static final String WEATHER_AT_TEMPLATE =
        WEATHER_SCHEMA
            + WEATHER_HOST
            + "/data/2.5/weather?lat=%f&lon=%f&units=metric&appid="
            + API_KEY;
    
    public static final String FORECAST_WEATHER_TEMPLATE =
        WEATHER_SCHEMA
            + WEATHER_HOST
            + "/data/2.5/forecast?lat=%f&lon=%f&units=metric&appid="
            + API_KEY;
    
    public static final String AIR_POLLUTION_AT_TEMPLATE =
        WEATHER_SCHEMA
            + WEATHER_HOST
            + "/data/2.5/air_pollution?lat=%f&lon=%f&appid="
            + API_KEY;
    
    public static final String AIR_POLLUTION_HISTORY_TEMPLATE =
        WEATHER_SCHEMA
            + WEATHER_HOST
            + "/data/2.5/air_pollution/history?lat=%f&lon=%f&start=%d&end=%d&appid="
            + API_KEY;
    
    public static final String LOCATION_SEARCH_TEMPLATE =
        WEATHER_SCHEMA
            + WEATHER_HOST
            + GEO_SERVICE
            + "direct?q=%s&limit=10&lang=pt&appid="
            + API_KEY;
    
    protected final Gson gson;
    private final AsyncRequest req;
    
    
    
    // API METHODS
    
    /**
     * Get WeatherInfo's from a local coordinates given a date interval
     * @param lat
     * @param lon
     * @return
     */
    public CompletableFuture<WeatherInfoDto> weatherAt(double lat, double lon) {
        var path =  String.format(WEATHER_AT_TEMPLATE, lat, lon);
        
        return req.get(path)
              .thenApply(reader -> {
                  try(reader) {
                      return gson.fromJson(reader, WeatherInfoDto.class);
                  }
                  catch(IOException e) {
                      throw new UncheckedIOException(e);
                  }
              });
    }
    
    
    
//    /**
//     * Get current air pollution metrics from a local coordinates
//     * @param lat
//     * @param lon
//     * @return
//     */
//    public PollutionInfoDto airPollutionAt(double lat, double lon) {
//        var path = String.format(AIR_POLLUTION_AT_TEMPLATE, lat, lon);
//
//        try(Reader reader = req.get(path)) {
//            PollutionInfoQueryDto pi =
//                gson.fromJson(reader, PollutionInfoQueryDto.class);
//            if (pi.list == null || pi.list.length != 1)
//                throw new WeatherApiException("response list must have one element");
//            return pi.list[0];
//        }
//        catch(IOException e) {
//            throw new UncheckedIOException(e);
//        }
//
//    }
//
//    /**
//     * Get WeatherInfo's forecast for a local coordinates
//     * @param lat
//     * @param lon
//     * @return
//     */
//    public List<WeatherInfoForecastDto> forecastWeatherAt(double lat, double lon) {
//        var path =  String.format(FORECAST_WEATHER_TEMPLATE, lat, lon);
//
//        try (Reader reader = req.get(path)) {
//            ForecastInfoDto finfo =
//                gson.fromJson(reader, ForecastInfoDto.class);
//            var local = finfo.getLocal();
//            return finfo.getForecast();
//        }
//        catch(IOException e) {
//            throw new UncheckedIOException(e);
//        }
//    }
//
//
//    /**
//     * Get local info given the name of the local
//     * @param location
//     * @return
//     */
//    public List<LocationDto> search(String location) {
//
//        var path =  String.format(LOCATION_SEARCH_TEMPLATE, location);
//
//        try (Reader reader = req.get(path)) {
//            LocationDto[] search = gson.fromJson(reader, LocationDto[].class);
//            return Arrays.asList(search);
//        }
//        catch(IOException e) {
//            throw new UncheckedIOException(e);
//        }
//    }
//
//    public List<PollutionInfoDto> pollutionHistoryAt(
//        double lati, double longi, LocalDate start, LocalDate end) {
//
//        var path = String.format(AIR_POLLUTION_HISTORY_TEMPLATE,
//            lati, longi,
//            TimeUtils.toUnixTime(start), TimeUtils.toUnixTime(end));
//
//        try (Reader reader = req.get(path)) {
//            PollutionInfoQueryDto winfo =
//                gson.fromJson(reader, PollutionInfoQueryDto.class);
//            return Arrays.asList(winfo.list);
//        }
//        catch(IOException e) {
//            throw new UncheckedIOException(e);
//        }
//    }
//
    public OpenWeatherWebApiAsync(AsyncRequest req) {
        this.req = req;
        gson = new Gson();
    }
    
    public OpenWeatherWebApiAsync() {
        this(new HttpAsyncRequest());
    }
    
   
}