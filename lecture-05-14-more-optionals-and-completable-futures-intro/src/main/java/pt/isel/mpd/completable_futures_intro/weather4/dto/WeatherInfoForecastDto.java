package pt.isel.mpd.completable_futures_intro.weather4.dto;

import pt.isel.mpd.completable_futures_intro.utils.PrintUtils;


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

    @Override
    public String toString() {
        return "{" + PrintUtils.EOL
            + "\tdateTime = " + dateTime() + PrintUtils.EOL
            + "\tdescription = " + description() + PrintUtils.EOL
            + "\tweather = " + weather() + PrintUtils.EOL
            + "}";
    }
}
