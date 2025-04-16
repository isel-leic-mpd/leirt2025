package pt.isel.mpd.weather2;

import pt.isel.mpd.weather2.dto.*;

import pt.isel.mpd.weather2.model.DayInfo;
import pt.isel.mpd.weather2.model.Location;
import pt.isel.mpd.weather2.model.WeatherInfo;
import pt.isel.mpd.weather2.queries.PipeIterable;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Supplier;

import static pt.isel.mpd.weather2.queries.PipeIterable.from;

public class OpenWeatherService {
	private  OpenWeatherWebApi api;

	public OpenWeatherService(OpenWeatherWebApi api) {
		this.api = api;
	}


	/**
	 *
	 * @param placeName
	 * @return
	 */
	public PipeIterable<Location> search(String placeName) {
		// CHANGED TO TURN LAZY
		return () -> from(api.search(placeName))
			   .map(this::dtoToLocation).iterator() ;
	}
	
	private PipeIterable<DayInfo> forecastAt(Location loc) {
		return 	() -> from(api.forecastWeatherAt(loc.getLatitude(), loc.getLongitude()))
				.map(dto -> dtoToDayInfo(dto, loc))
				.distinct().iterator();
	}
	
	private PipeIterable<WeatherInfo> weatherDetail(Location loc, DayInfo di) {
		return from(api.forecastWeatherAt(loc.getLatitude(), loc.getLongitude()))
			.filter(dto -> dto.dateTime().toLocalDate().equals(di.getDate()))
			.map(this::dtoToWeatherInfo);
	}
	
	private  Location dtoToLocation(LocationDto dto) {
		return  new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon(),
			loc -> forecastAt(loc)
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
