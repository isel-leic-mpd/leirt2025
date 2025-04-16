package pt.isel.mpd.weather2;

import pt.isel.mpd.weather2.model.*;

import pt.isel.mpd.weather2.queries.PipeIterable;
import pt.isel.mpd.weather2.requests.CounterRequest;
import pt.isel.mpd.weather2.requests.HttpRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WeatherServiceTests {

	@Test
	public void get_locations_named_lisbon() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi(req)
			);
		
		PipeIterable<Location> locations =
			service.search("Lisboa");
 		assertEquals(0, req.getCount());
		for(var loc : locations) {
			System.out.println(loc);
		}
		assertEquals(1, req.getCount());
		
	}
	
	


	@Test
	public void getForecastForLisbonTest() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi(req));

		// TODO
		PipeIterable<DayInfo> forecastWeather =
			service.search("Lisbon")
		   	.filter(l -> l.getCountry().equals("PT"))
			.flatMap(l -> l.forecast());

	 	assertEquals(0, req.getCount());
		long nDays = forecastWeather.count();
		assertEquals(2, req.getCount());
		assertEquals(6, nDays);

		System.out.println("DayInfo list size: " + nDays);
		forecastWeather.forEach(System.out::println);
	}

	@Test
	public void getForecastDetailForTomorrowAtLisbonTest() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		var service =
			new OpenWeatherService(new OpenWeatherWebApi(req));

		// TODO
		var tomorrowTemps =
			service.search("Lisboa")
				   .filter(l -> l.getCountry().equals("PT"))
				   .flatMap(l -> l.forecast())
				   .filter(di -> di.getDate().equals(LocalDate.now().plusDays(1)))
				   .flatMap(di -> di.temperatures());
		assertEquals(0, req.getCount());
		assertEquals(8, tomorrowTemps.count());
		tomorrowTemps.forEach(System.out::println);
	}
}
