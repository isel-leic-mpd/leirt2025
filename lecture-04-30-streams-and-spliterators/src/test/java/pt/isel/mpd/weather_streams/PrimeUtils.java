package pt.isel.mpd.weather_streams;

public class PrimeUtils {
    public static boolean isPrime(long n) {
        if (n == 2) return true;
        if (n < 2 || n % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2)
            if (n % i == 0) return false;
        return true;
    }
    
    public static long nextPrime(long p) {
        if (p < 2) return 2;
        else {
            if (p == 2) return 3;
            p = (p % 2 == 0) ? p+1 : p+2;
            while(!isPrime(p)) p += 2;
        }
        return p;
    }
    
}
