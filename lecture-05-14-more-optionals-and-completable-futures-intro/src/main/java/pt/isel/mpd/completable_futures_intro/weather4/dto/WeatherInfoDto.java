package pt.isel.mpd.completable_futures_intro.weather4.dto;

import pt.isel.mpd.completable_futures_intro.utils.PrintUtils;

public class WeatherInfoDto extends WeatherInfoBaseDto {

    private String name;
    private  int timezone;

    public String local() {
        return name;
    }

    @Override
    public String toString() {
        return "{" + PrintUtils.EOL
               + "\tdateTime = " + dateTime() + PrintUtils.EOL
               + "\tdescription = " + description() + PrintUtils.EOL
               + "\tweather = " + weather() + PrintUtils.EOL
               + "\tname = " + local() + PrintUtils.EOL
               + "\ttimezone = " + timezone + PrintUtils.EOL
               + "}";
    }
}
