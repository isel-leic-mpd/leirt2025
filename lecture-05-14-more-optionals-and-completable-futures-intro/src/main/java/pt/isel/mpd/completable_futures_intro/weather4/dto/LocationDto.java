package pt.isel.mpd.completable_futures_intro.weather4.dto;

public  class LocationDto {
    private String name;
    private double lat;
    private double lon;
    String country;

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCountry() {
        return country;
    }

    public String toString() {
        return "{ name = " + name +
                ", lat = " + lat +
                ", lon = " + lon +
                ", country = " + country +
                " }";

    }
}
