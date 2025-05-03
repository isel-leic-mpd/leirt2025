package pt.isel.mpd.weather2;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.weather2.dto.LocationDto;
import pt.isel.mpd.weather2.dto.PollutionInfoDto;
import pt.isel.mpd.weather2.dto.WeatherInfoDto;
import pt.isel.mpd.weather2.dto.WeatherInfoForecastDto;
import pt.isel.mpd.weather2.requests.HttpRequest;
import pt.isel.mpd.weather2.requests.MockRequest;
import pt.isel.mpd.weather2.requests.SaverRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pt.isel.mpd.weather2.queries.PipeIterable.from;


public class WeatherApiTests {
    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;
    
    
    // direct API queries
    
    @Test
    public void get_weather_at_lisbon_now() {
        var saverReq = new SaverRequest(new HttpRequest());
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(saverReq);
        WeatherInfoDto winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );
        System.out.println(winfo);
    }
    
    @Test
    public void get_weather_at_lisbon_now_using_mock() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new MockRequest());
        WeatherInfoDto winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );
        System.out.println(winfo);
    }
    
    @Test
    public void get_air_pollution_in_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        
        PollutionInfoDto pi = webApi.airPollutionAt(
            LISBON_LAT, LISBON_LONG);
        System.out.println(pi);
    }
    
    @Test
    public void get_lisbon_forecast() {
        
        var weatherApi = new OpenWeatherWebApi(new HttpRequest());
        
        var forecast =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);
        for (var df : forecast) {
            System.out.println(df);
        }
    }
    
    @Test
    public void get_air_pollution_history_by_period() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate end = LocalDate.of(2025, 3, 8);
        List<PollutionInfoDto> pinfo =
            webApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,start,end);
        System.out.println(pinfo.size());
        for(PollutionInfoDto pi : pinfo) {
            System.out.println(pi);
        }
        assertEquals(121, pinfo.size() );
    }
    
    @Test
    public void get_location_info_by_name() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        String localName = "Lisboa";
        
        List<LocationDto> locations = webApi.search(localName);
        for(var loc : locations)
            System.out.println(loc);
        assertEquals(5, locations.size());
    }
    
    // value added queries
    
    @Test
    public void get_sunny_periods_for_lisbon_forecast_test() {
        
        var weatherApi = new OpenWeatherWebApi(new HttpRequest());
        
        var forecast =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);
        
        var res = from(forecast)
                 .filter( wf -> wf.description().contains("sun"));
            
        
        System.out.println(res);
    }
    
    @Test
    public void get_rainy_periods_for_lisbon_forecast_test() {
        var weatherApi = new OpenWeatherWebApi(new HttpRequest());
        
        var forecast =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);
        
        var res = from(forecast)
            .filter(  wf -> wf.description().contains("rain"));
         
        System.out.println(res);
    }
    
    @Test
    public void get_max_temp_for_rainy_periods_in_lisbon_forecast_using_PipeIterable() {
        var weatherApi = new OpenWeatherWebApi(new HttpRequest());
        
        var maxTempInRainyPeriod =
            from( weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG))
            .filter( p -> p.description().contains("rain"))
            .max(Comparator.comparingDouble(WeatherInfoForecastDto::maxTemp));
            
        System.out.println(maxTempInRainyPeriod);
    }
    
    
    record DatedValue<T>  (LocalDateTime dateTime, T value) {}
    
    
    @Test
    public void getForecastTemperatureSamplesInRainyDaysAtLisbonCoordinatesUsingPipeIterable() {
        var weatherApi = new OpenWeatherWebApi(new HttpRequest());
        
        var forecastTemps =
                from(weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG))
                .filter(f -> f.description().contains("rain"))
                .map(wf ->
                    new DatedValue<>(wf.dateTime(), wf.temp()));
              
        
        System.out.println("result size= "+ forecastTemps.count());
        
        for (var t : forecastTemps) {
            System.out.println(t);
        }
    }
    
    
    @Test
    public void getForecastWeatherForLocalsNamedLisbonTestUsingPipeIterable() {
        
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        var forecasts = from(webApi.search("Lisbon") )
            .flatMap(loc-> from(webApi.forecastWeatherAt(loc.getLat(), loc.getLon())));
            
          
        for(var wif : forecasts) {
            System.out.println(wif);
        }
    }
    
}
