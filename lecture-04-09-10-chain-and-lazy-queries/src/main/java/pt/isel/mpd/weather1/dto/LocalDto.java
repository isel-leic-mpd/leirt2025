package pt.isel.mpd.weather1.dto;

import pt.isel.mpd.weather1.utils.TimeUtils;

import java.time.LocalDateTime;

import static pt.isel.mpd.weather1.utils.PrintUtils.EOL;

public class LocalDto {
    private String name;
    private String country;
    private int population;
    private int timezone;
    private long sunrise;
    private long sunset;

    public String getName() { return name; }
    public String getCountry() { return country; }
    public int getPopulation() { return population; }
    public int getTimezone() { return timezone; }
    public LocalDateTime getSunRise() { return TimeUtils.fromUnixTime(sunrise); }
    public LocalDateTime getSunSet() { return TimeUtils.fromUnixTime(sunset); }

    public String toString() {
        return "{" + EOL +
                "\tname = " + getName() + EOL +
                "\tcountry = " + getCountry() + EOL +
                "\tpopulation = " + getPopulation() + EOL +
                "\ttimezone = " + getTimezone() + EOL +
                "\tsunrise = " + getSunRise() + EOL +
                "\tsunset = " + getSunSet() + EOL +
            "}";
    }
}
