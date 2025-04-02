package pt.isel.mpd.weather0.dto;

import java.time.LocalDate;

import static pt.isel.mpd.weather0.utils.PrintUtils.EOL;

public class WeatherInfoForecastDto extends WeatherInfoBaseDto {
    private String dt_txt;
    private String date_text;

    @Override
    protected String get_formatted_date() {
        if (date_text == null) {
            String parts[] = dt_txt.split(" ");
            date_text = parts[0] + "T" + parts[1];
        }
        return date_text;
    }

    public LocalDate getDate() {
        var dt = dateTime();
        return LocalDate.of(dt.getYear(), dt.getMonth(), dt.getDayOfMonth());
    }
    @Override
    public String toString() {
        return "{" + EOL
            + "\tdateTime = " + dateTime() + EOL
            + "\tdescription = " + description() + EOL
            + "\tweather = " + weather() + EOL
            + "}";
    }
}
