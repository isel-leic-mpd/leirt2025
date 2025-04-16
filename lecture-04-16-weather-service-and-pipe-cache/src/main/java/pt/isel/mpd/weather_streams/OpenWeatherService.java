package pt.isel.mpd.weather_streams;

import pt.isel.mpd.weather2.OpenWeatherWebApi;
import pt.isel.mpd.weather2.dto.LocationDto;
import pt.isel.mpd.weather2.dto.WeatherInfoForecastDto;
import pt.isel.mpd.weather_streams.model.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import static pt.isel.mpd.weather2.queries.PipeIterable.from;

public class OpenWeatherService {
	private OpenWeatherWebApi api;

	public OpenWeatherService(OpenWeatherWebApi api) {
		this.api = api;
	}


	/**
	 *
	 * @param placeName
	 * @return
	 */
	public Stream<Location> search(String placeName) {
		// CHANGE TO TURN LAZY
		return api.search(placeName).stream().map(this::dtoToLocation);
	}

	
	private Stream<DayInfo> forecastAt(Location loc) {
		return 	api.forecastWeatherAt(loc.getLatitude(), loc.getLongitude()).stream()
				.map(dto -> dtoToDayInfo(dto, loc))
				.distinct();
	}

	private Stream<WeatherInfo> weatherDetail(Location loc, DayInfo di) {
		return api.forecastWeatherAt(loc.getLatitude(), loc.getLatitude()).stream()
			.filter(dto -> dto.dateTime().toLocalDate().equals(di.getDate()))
			.map(this::dtoToWeatherInfo);
	}
	
	private  Location dtoToLocation(LocationDto dto) {
		return  new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon(),
			(Location l) -> forecastAt(l)
		);
	}

	private  WeatherInfo dtoToWeatherInfo(WeatherInfoForecastDto dto) {
		return new WeatherInfo(
			dto.dateTime(),
			dto.temp(),
			dto.description(),
			dto.humidity(),
			dto.feelsLike());
	}
	
	public DayInfo dtoToDayInfo(WeatherInfoForecastDto dto, Location loc) {
		return new DayInfo(
			dto.dateTime().toLocalDate(),
			dto.maxTemp(),
			dto.minTemp(),
			dto.description(),
			di -> weatherDetail(loc, di)
		);
	}
}
