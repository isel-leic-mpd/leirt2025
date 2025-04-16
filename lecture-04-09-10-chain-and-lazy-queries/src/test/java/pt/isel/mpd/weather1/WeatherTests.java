package pt.isel.mpd.weather1;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.weather1.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pt.isel.mpd.weather1.queries.PipeIterable.from;
import static pt.isel.mpd.weather1.queries.Queries.*;

public class WeatherTests {
    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;
    
    private static Iterable<WeatherInfoForecastDto> getSunnyPeriodsForForecast(
        List<WeatherInfoForecastDto> forecast) {
        var result = new ArrayList<WeatherInfoForecastDto>();
        for(var f : forecast) {
            if (f.description().contains("sun")) result.add(f);
        }
        return result;
    }
    
    private static Iterable<WeatherInfoForecastDto> getRainyPeriodsForForecast(
        List<WeatherInfoForecastDto> forecast) {
        
        var result = new ArrayList<WeatherInfoForecastDto>();
        for(var f : forecast) {
            if (f.description().contains("rain")) result.add(f);
        }
        return result;
    }
    
    private static Iterable<PollutionInfoDto> getTooMuchCOForPollutionData(
        Iterable<PollutionInfoDto> pollutionData) {
        var result = new ArrayList<PollutionInfoDto>();
        for(var p  : pollutionData) {
            if (p.getCO() >= PollutionInfoDto.MAX_ACCEPTABLE_CO) {
                result.add(p);
            }
        }
        return result;
    }
    
    private static Iterable<PollutionInfoDto> getGoodCOForPollutionData(
        Iterable<PollutionInfoDto> pollutionData) {
        var result = new ArrayList<PollutionInfoDto>();
        for(var p  : pollutionData) {
            if (p.getCO() < PollutionInfoDto.MAX_GOOD_CO) {
                result.add(p);
            }
        }
        return result;
    }
    
    // direct API queries
    
    @Test
    public void get_weather_at_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        WeatherInfoDto winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );
        System.out.println(winfo);
    }
    
    @Test
    public void get_air_pollution_in_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        
        PollutionInfoDto pi = webApi.airPollutionAt(
            LISBON_LAT, LISBON_LONG);
        System.out.println(pi);
    }
    
    @Test
    public void get_lisbon_forecast_test() {
        
        var weatherApi = new OpenWeatherWebApi();
        
        var forecast =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);
        for (var df : forecast) {
            System.out.println(df);
        }
    }
    
    @Test
    public void get_air_pollution_history_by_period() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
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
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        String localName = "Lisboa";
        
        List<LocationDto> locations = webApi.search(localName);
        for(var loc : locations)
            System.out.println(loc);
        assertEquals(5, locations.size());
    }
    
    // value added queries
    
    @Test
    public void get_sunny_periods_for_lisbon_forecast_test() {
        
        var weatherApi = new OpenWeatherWebApi();
        
        var forecast =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);
        
        var res = filter(forecast,
                wf -> wf.description().contains("sun"));
            
        
        System.out.println(res);
    }
    
    @Test
    public void get_rainy_periods_for_lisbon_forecast_test() {
        var weatherApi = new OpenWeatherWebApi();
        
        var forecast =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);
        
        var res = filter(forecast,
            wf -> wf.description().contains("rain"));
        
        System.out.println(res);
    }
    
    
   
    @Test
    public void get_max_temp_for_rainy_periods_in_lisbon_forecast_imperative() {
        var weatherApi = new OpenWeatherWebApi();
        WeatherInfoForecastDto maxTempInRainyPeriod = null;
        var forecast = weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);
        for (var f : forecast) {
            if (f.description().contains("rain")) {
                if (maxTempInRainyPeriod == null || f.maxTemp() > maxTempInRainyPeriod.maxTemp()) {
                    maxTempInRainyPeriod = f;
                }
            }
        }
        
        System.out.println(maxTempInRainyPeriod);
    }
    
    @Test
    public void get_max_temp_for_rainy_periods_in_lisbon_forecast_using_chaining() {
        var weatherApi = new OpenWeatherWebApi();
        
        var maxTempInRainyPeriod =
            max(
                filter(
                    weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG),
                    p -> p.description().contains("rain")
                ),
                Comparator.comparingDouble(WeatherInfoForecastDto::maxTemp)
            );
           
        System.out.println(maxTempInRainyPeriod);
    }
    
    @Test
    public void get_max_temp_for_rainy_periods_in_lisbon_forecast_using_streams() {
        var weatherApi = new OpenWeatherWebApi();
        
        var maxTempInRainyPeriod =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG).stream()
            .filter(p -> p.description().contains("rain"))
            .max(Comparator.comparingDouble(WeatherInfoForecastDto::maxTemp));
        System.out.println(maxTempInRainyPeriod);
    }
    
    
    
    record DatedValue<T>  (LocalDateTime dateTime, T value) {}
    
    
    @Test
    public void getForecastTemperatureSamplesInRainyDaysAtLisbonCoordinatesUsingStreams() {
        var weatherApi = new OpenWeatherWebApi();
        
        var forecastTemps =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG).stream()
            .filter(f -> f.description().contains("rain"))
            .map(wf ->
                new DatedValue<>(wf.dateTime(), wf.temp()))
            .toList();
        System.out.println("result size= "+ forecastTemps.size());
        for (var t : forecastTemps) {
            System.out.println(t);
        }
    }
    
    @Test
    public void getForecastTemperatureSamplesInRainyDaysAtLisbonCoordinatesUsingQueries() {
        var weatherApi = new OpenWeatherWebApi();
        
        
        var forecastTemps =
            map(
                filter(
                    weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG),
                    f -> f.description().contains("rain")
                    
                ),
                f -> new DatedValue<>(f.dateTime(), f.temp())
            );
        System.out.println("result size= "+ count(forecastTemps));
        for (var t : forecastTemps) {
            System.out.println(t);
        }
        
    }
    
    
    @Test
    public void getForecastTemperatureSamplesInRainyDaysAtLisbonCoordinatesUsingPipeIterable() {
        var weatherApi = new OpenWeatherWebApi();
        
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
    public void getForecastWeatherForLocalsNamedLisbonImperative() {
        
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<LocationDto> locs = webApi.search("Lisbon");
        assertTrue(locs.size() > 0);
        
        List<WeatherInfoForecastDto> forecasts = new ArrayList<>();
        
        for (var l : locs) {
            List<WeatherInfoForecastDto> winfo =
                    webApi.forecastWeatherAt(l.getLat(), l.getLon());
            
            for(var wf : winfo) {
                forecasts.add(wf);
            }
        }
        
        for(var wif : forecasts) {
            System.out.println(wif);
        }
    }
    
    @Test
    public void getForecastWeatherForLocalsNamedLisbonTestUsingStreams() {
        
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        var forecasts = from(webApi.search("Lisbon") )
            .flatMap(loc-> from(webApi.forecastWeatherAt(loc.getLat(), loc.getLon())));
            
          
        for(var wif : forecasts) {
            System.out.println(wif);
        }
    }
    
}
