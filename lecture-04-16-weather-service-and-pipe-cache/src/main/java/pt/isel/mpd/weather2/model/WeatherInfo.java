package pt.isel.mpd.weather2.model;

import java.time.LocalDateTime;

public class WeatherInfo {
	private final LocalDateTime time;
	private final double tempC;
	private final String description;
	private final int  humidity;
	private final double feelsLikeC;

	public WeatherInfo(LocalDateTime time, double tempC, String description, int humidity, double feelsLikeC) {
		this.time = time;
		this.tempC = tempC;
		this.description = description;
		this.humidity = humidity;
		this.feelsLikeC = feelsLikeC;
	}

	public LocalDateTime getLocalTime() { return time; }

	public double getTempC() { return tempC; }
	public String getDescription() { return description; }
	public int getHumidity() { return humidity; }
	public double getFeelsLikeC() { return feelsLikeC; }


	@Override
	public  String toString() {
		return "dto.WeatherInfo{" +
			"  time=" + time +
			", tempC=" + tempC +
			", '" + description + '\'' +
			", humidity=" + humidity +
			", feelsLikeC=" + feelsLikeC +
			'}';
	}
}
