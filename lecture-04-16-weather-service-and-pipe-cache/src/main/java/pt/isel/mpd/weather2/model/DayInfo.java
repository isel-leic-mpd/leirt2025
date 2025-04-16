package pt.isel.mpd.weather2.model;

import pt.isel.mpd.weather2.queries.PipeIterable;

import java.time.LocalDate;
import java.util.function.Function;

public class DayInfo {
	private LocalDate date;
	private double maxTempC;
	private double minTempC ;
	private String description;

	private   Function<DayInfo,PipeIterable<WeatherInfo>> temperatures;

	public DayInfo(LocalDate date, double maxTempC,
				   double minTempC, String description,
				   Function<DayInfo,PipeIterable<WeatherInfo>> temperatures) {
		this.date = date;
		this.maxTempC = maxTempC;
		this.minTempC = minTempC;
		this.description = description;
		this.temperatures = temperatures;
	
	}

	// accessors
	public LocalDate getDate()      { return date; }
	public double getMaxTemp()      { return maxTempC; }
	public double getMinTemp()      { return minTempC; }
	public String getDescription()  { return description; }

	public PipeIterable<WeatherInfo> temperatures() {
		// TO CHANGE
		return temperatures.apply(this);
	}
	
	@Override
	public String toString() {
		return "{"
			+ date
			+ ", "				+ description
			+ ", min="          + minTempC
			+ ", max="          + maxTempC
			+ "}";
	}
	
	@Override
	public int hashCode() {
		return getDate().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != DayInfo.class) return false;
		var other = (DayInfo) obj;
		var result = getDate().equals(other.getDate());
		return result;
	}
}
