package pt.isel.mpd.weather0;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.weather0.dto.*;
import static pt.isel.mpd.weather0.queries.Queries.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

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
    public void get_bad_co_for_lisbon_pollution_data_at_march_2024_test() {
        var weatherApi = new OpenWeatherWebApi();
        
        var pollutionData =
            weatherApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,
                LocalDate.of(2024, 03, 01),
                LocalDate.of(2024,03, 25 ));
        
        var res =
            getTooMuchCOForPollutionData(pollutionData);
        
        System.out.println(res);
    }
    
    
    @Test
    public void get_good_co_for_lisbon_pollution_data_at_march_2024_test() {
        var weatherApi = new OpenWeatherWebApi();
        
        var pollutionData =
            weatherApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,
                LocalDate.of(2024, 03, 01),
                LocalDate.of(2024,03, 25 ));
        
        var res =
            getGoodCOForPollutionData(pollutionData);
        
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
    public void getMaxTemperatureInLisbonForecastUsingStreams() {
        var weatherApi = new OpenWeatherWebApi();
        
        var forecast =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG).stream()
            .map( wf -> new DatedValue<>(wf.dateTime(), wf.maxTemp()))
            .max(Comparator.comparingDouble(DatedValue::value));
        System.out.println(forecast.get());
    }
    
    @Test
    public void getForecastWeatherForLisbonTest() {
        int NDAYS = 5;
        int SAMPLES_PER_DAY = 8;
        
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<LocationDto> locs = webApi.search("Lisbon");
        assertTrue(locs.size() > 0);
        
        LocationDto loc = null;
        for (var l : locs) {
            if (l.getCountry().equals("PT")) {
                loc = l;
            }
        }
        assertNotNull(loc);
        
        List<WeatherInfoForecastDto> winfo =
            webApi.forecastWeatherAt(loc.getLat(), loc.getLon());
        
        for(var wif : winfo) {
            System.out.println(wif);
        }
        
        assertEquals(NDAYS*SAMPLES_PER_DAY, winfo.size());
    }
    
    @Test
    public void getForecastWeatherForLisbonTestUsingStreams() {
        int NDAYS = 5;
        int SAMPLES_PER_DAY = 8;
        
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        var forecasts = webApi.search("Lisbon").stream()
            .filter(loc -> loc.getCountry().equals("PT"))
            .flatMap(loc-> webApi.forecastWeatherAt(loc.getLat(), loc.getLon()).stream())
            .toList();
          
        for(var wif : forecasts) {
            System.out.println(wif);
        }
        
        assertTrue(forecasts.size() == NDAYS*SAMPLES_PER_DAY);
        
    }
    
}
