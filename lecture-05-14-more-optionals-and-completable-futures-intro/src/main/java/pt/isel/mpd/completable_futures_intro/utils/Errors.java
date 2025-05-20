package pt.isel.mpd.completable_futures_intro.utils;

public class Errors {
    public static void TODO(String method) {
        throw new RuntimeException("'" + method + "'" + " method or constructor not Implemented!");
    }
    
    public static void TO_COMPLETE(String method) {
        throw new RuntimeException("'" + method + "'" + " method or constructor uncompleted!");
    }
}