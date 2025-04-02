package pt.isel.mpd.weather0.dto;

import com.google.gson.annotations.SerializedName;
import pt.isel.mpd.weather0.utils.TimeUtils;

import java.time.LocalDateTime;

import static pt.isel.mpd.weather0.utils.PrintUtils.EOL;

public class PollutionInfoDto {
    public static final double MAX_GOOD_CO = 4400;
    public static final double MAX_ACCEPTABLE_CO = 12400;
    
    public static final double MAX_GOOD_SO2 = 20;
    public static final double MAX_ACCEPTABLE_SO2 = 250;
    
    public static final double MAX_GOOD_NO2 = 40;
    public static final double MAX_ACCEPTABLE_NO2 = 150;
    
    public static final double MAX_GOOD_CPC = 20;
    public static final double MAX_ACCEPTABLE_CPC = 100;
    
    public static final double MAX_GOOD_FPC = 10;
    public static final double MAX_ACCEPTABLE_FPC = 50;
    
    public static final double MAX_GOOD_O3 = 60;
    public static final double MAX_ACCEPTABLE_O3 = 140;
    
    public static final double MAX_GOOD_QI = 1;
    public static final double MAX_ACCEPTABLE_QI = 4;
    
    public static class Main {
        @SerializedName("aqi")
        int qualityIndex;
    }

    public static class Components {
        double co;      // monóxido de carbono
        double no;      // óxido de azoto
        double no2;     // dióxido de azoto
        double o3;      // ozono
        double so2;     // dioxido de enxofre
        double pm2_5;   // particulas finas
        double pm2_10;  // partículas grossas
        double nh3;     // amoníaco
    }

    private Main main;
    private Components components;
    private long dt;

    public double getCO() { return components.co; }

    public double getNO() { return components.no; }

    public double getNO2() { return components.no2; }

    public double getSO2() { return components.so2; }

    public double getO3() { return components.o3; }
    
    public double getFPC() { return components.pm2_5; }

    public double getCPC() { return components.pm2_10; }

    public double getNH3() { return components.nh3; }

    public double getQI() { return main.qualityIndex; }

    public LocalDateTime dateTime() {
        return TimeUtils.fromUnixTime(dt);
    }

    @Override
    public String toString() {
        return "{" + EOL +
                    "\tdate = " + dateTime() + EOL +
                    "\tco= " + getCO() + EOL +
                    "\tno= " + getNO() + EOL +
                    "\tno2= " + getNO2() + EOL +
                    "\to3= "  + getO3() + EOL +
                    "\toso2= "  + getSO2() + EOL +
                    "\tfpc= "  + getFPC() + EOL +
                    "\tcpc= "  + getCPC() + EOL +
                    "\tnh3= "  + getNH3() + EOL +
                    "\tquality = " + getQI() + EOL +
                "}";
    }
    
    public boolean isBadQI() {
        return getQI() >= MAX_ACCEPTABLE_QI;
    }
    
    public boolean isBadNO2() {
        return getNO2() >= MAX_ACCEPTABLE_NO2;
    }
    
    public boolean isBadCPC() {
        return getCPC() >= MAX_ACCEPTABLE_CPC;
    }
    
    public boolean isBadFPC() {
        return getFPC() >= MAX_ACCEPTABLE_FPC;
    }
    
    public boolean isBadO3() {
        return getO3() >= MAX_ACCEPTABLE_O3;
    }
    
    public boolean isGoodQI() {
        return getQI() <= MAX_GOOD_QI;
    }
    
    public boolean isGoodNO2() {
        return getNO2() <= MAX_GOOD_NO2;
    }
    
    public boolean isGoodCPC() {
        return getCPC() <= MAX_GOOD_CPC;
    }
    
    public boolean isGoodFPC() {
        return getFPC() <= MAX_GOOD_FPC;
    }
    
    public boolean isGoodO3() {
        return getO3() <= MAX_GOOD_O3;
    }
}
