package pt.isel.mpd.completable_futures_intro.weather4.model;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Stream;

public class DayInfo implements Comparable<DayInfo> {
	private LocalDate date;
	private double maxTempC;
	private double minTempC ;
	private String description;

	private   Function<DayInfo, Stream<WeatherInfo>> temperatures;

	public DayInfo(LocalDate date, double maxTempC,
				   double minTempC, String description,
				   Function<DayInfo,Stream<WeatherInfo>> temperatures) {
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

	public Stream<WeatherInfo> temperatures() {
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
	public boolean equals(Object other) {
		if (other == this) return true;
		if (other == null || other.getClass() != getClass()) return false;
		DayInfo di = (DayInfo) other;
		return di.date.equals(date);
	}
	
	@Override
	public int compareTo(DayInfo o) {
		 return date.compareTo(o.date);
	}
}
