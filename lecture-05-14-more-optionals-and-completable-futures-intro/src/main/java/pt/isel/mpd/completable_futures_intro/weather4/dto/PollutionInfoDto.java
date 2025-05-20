package pt.isel.mpd.completable_futures_intro.weather4.dto;

import com.google.gson.annotations.SerializedName;
import pt.isel.mpd.completable_futures_intro.utils.PrintUtils;
import pt.isel.mpd.completable_futures_intro.utils.TimeUtils;

import java.time.LocalDateTime;

public class PollutionInfoDto {
    public static final double MAX_GOOD_CO = 4400;
    public static final double MIN_WORSE_CO = 12400;
    
    public static final double MAX_GOOD_SO2 = 20;
    public static final double MIN_WORSE_SO2 = 250;
    
    public static final double MAX_GOOD_NO2 = 40;
    public static final double MIN_WORSE_NO2 = 150;
    
    public static final double MAX_GOOD_CPC = 20;
    public static final double MIN_WORSE_CPC = 100;
    
    public static final double MAX_GOOD_FPC = 10;
    public static final double MIN_WORSE_FPC = 50;
    
    public static final double MAX_GOOD_O3 = 60;
    public static final double MIN_WORSE_O3 = 140;
    
    public static final double MAX_GOOD_QI = 1;
    public static final double MIN_WORSE_QI = 4;
    
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
        return "{" + PrintUtils.EOL +
                    "\tdate = " + dateTime() + PrintUtils.EOL +
                    "\tco= " + getCO() + PrintUtils.EOL +
                    "\tno= " + getNO() + PrintUtils.EOL +
                    "\tno2= " + getNO2() + PrintUtils.EOL +
                    "\to3= "  + getO3() + PrintUtils.EOL +
                    "\toso2= "  + getSO2() + PrintUtils.EOL +
                    "\tfpc= "  + getFPC() + PrintUtils.EOL +
                    "\tcpc= "  + getCPC() + PrintUtils.EOL +
                    "\tnh3= "  + getNH3() + PrintUtils.EOL +
                    "\tquality = " + getQI() + PrintUtils.EOL +
                "}";
    }
    
    public boolean isBadQI() {
        return getQI() >= MIN_WORSE_QI;
    }
    
    public boolean isBadNO2() {
        return getNO2() >= MIN_WORSE_NO2;
    }
    
    public boolean isBadCPC() {
        return getCPC() >= MIN_WORSE_CPC;
    }
    
    public boolean isBadFPC() {
        return getFPC() >= MIN_WORSE_FPC;
    }
    
    public boolean isBadO3() {
        return getO3() >= MIN_WORSE_O3;
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
