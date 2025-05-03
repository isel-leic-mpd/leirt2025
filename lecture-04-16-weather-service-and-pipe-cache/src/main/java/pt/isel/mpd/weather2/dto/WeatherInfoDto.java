package pt.isel.mpd.weather2.dto;

import static pt.isel.mpd.weather2.utils.PrintUtils.EOL;

public class WeatherInfoDto extends WeatherInfoBaseDto {

    private String name;
    private  int timezone;

    public String local() {
        return name;
    }

    @Override
    public String toString() {
        return "{" + EOL
               + "\tdateTime = " + dateTime() + EOL
               + "\tdescription = " + description() + EOL
               + "\tweather = " + weather() + EOL
               + "\tname = " + local() + EOL
               + "\ttimezone = " + timezone + EOL
               + "}";
    }
}
