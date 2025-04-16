package pt.isel.mpd.weather_streams;

import org.junit.jupiter.api.Test;

import pt.isel.mpd.weather2.OpenWeatherWebApi;
import pt.isel.mpd.weather_streams.model.*;
import pt.isel.mpd.weather2.requests.*;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WeatherServiceTests {

	@Test
	public void get_locations_named_lisbon() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi(req)
			);
		
		Stream<Location> locations =
			service.search("Lisboa");
 		assertEquals(0, req.getCount());
		locations.forEach(System.out::println);
		assertEquals(1, req.getCount());
	}

	@Test
	public void getForecastForLisbonTest() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi(req));

		// TODO
		var forecastWeather =
			service.search("Lisbon")
		   	.filter(l -> l.getCountry().equals("PT"))
			.flatMap(l -> l.forecast())
			.toList();

	 	assertEquals(2, req.getCount());
		long nDays = forecastWeather.size();
		assertEquals(2, req.getCount());
		assertEquals(6, nDays);

		System.out.println("DayInfo list size: " + nDays);
		forecastWeather.forEach(System.out::println);
	}

	@Test
	public void getForecastDetailForTomorrowAtLisbonTest() {
		var service =
			new OpenWeatherService(new OpenWeatherWebApi(new HttpRequest()));

		// TODO
		var tomorrowTemps =
			service.search("Lisboa")
				   .filter(l -> l.getCountry().equals("PT"))
				   .flatMap(l -> l.forecast())
				   .filter(di -> di.getDate().equals(LocalDate.now().plusDays(1)))
				   .flatMap(di -> di.temperatures());
		
		assertEquals(8, tomorrowTemps.count());
		tomorrowTemps.forEach(System.out::println);
	}
}
