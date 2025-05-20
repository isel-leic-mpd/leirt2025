package pt.isel.mpd.completable_futures_intro.weather4;

import pt.isel.mpd.completable_futures_intro.weather4.dto.LocationDto;
import pt.isel.mpd.completable_futures_intro.weather4.dto.WeatherInfoForecastDto;
import pt.isel.mpd.completable_futures_intro.weather4.model.DayInfo;
import pt.isel.mpd.completable_futures_intro.weather4.model.Location;
import pt.isel.mpd.completable_futures_intro.weather4.model.WeatherInfo;

import java.time.LocalDate;
import java.util.stream.Stream;


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
	public Stream<Location> search(String placeName) {
		// CHANGED TO TURN LAZY
		return api.search(placeName).stream()
			  .map(this::dtoToLocation);
}

	
	private Stream<DayInfo> forecastAt(Location loc) {
		// CHANGE TO TURN LAZY
		return 	api.forecastWeatherAt(loc.getLatitude(), loc.getLongitude()).stream()
				.map(dto -> dtoToDayInfo(dto, loc))
				.distinct();
	}

	private Stream<WeatherInfo> weatherDetail(Double lat,
											  Double lon,
											  LocalDate day) {
		 return api.forecastWeatherAt(lon, lat).stream()
				.filter(dto -> dto.dateTime().toLocalDate().equals(day))
				.map(this::dtoToWeatherInfo);
			 
	}

	private Stream<WeatherInfo> weatherDetail(Location loc, DayInfo di) {
		return weatherDetail(loc.getLatitude(), loc.getLongitude(), di.getDate());
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

	// utilizem neste método a mesma técnica usada no Location
	// para que a obtenção das abservações de temperatura do dia só ocorra
	// quando for chamado o método temperatures de DayInfo
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
