package pt.isel.mpd.completable_futures_intro.utils;

public class ThreadUtils {
    
    // auxiliary methods for avoid explicitly throws in code
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch(InterruptedException e) { }
    }
    
    public static void join(Thread t) {
        try {
            t.join();
        }
        catch(InterruptedException e) { }
    }
    
}
