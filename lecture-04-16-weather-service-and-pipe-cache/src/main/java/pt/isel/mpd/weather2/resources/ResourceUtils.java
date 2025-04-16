package pt.isel.mpd.weather2.resources;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceUtils {
    private final static String CACHE_NAME= "queries_cache/";
    private  static String CACHE_PATH;
    
    static {
        URL url = ClassLoader.getSystemResource(CACHE_NAME);
        try {
            File file = new File(url.toURI());
            CACHE_PATH = file.getAbsolutePath() + "/";
        }
        catch(URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static String convert(String path) {
        var start = 0;
        if (path.startsWith("https://")) start = 8;
        else if (path.startsWith("http://")) start = 7;
        var end = path.lastIndexOf('&');
        if (end == -1) end = path.lastIndexOf('?');
        if (end == -1) end = path.length();
        return  path.substring(start, end)
            .replace('&', '-')
            .replace('/', '.')
            .replace( '?','-')
            .replace( ',','-');
    }
    
    public static Reader openResource(String path) {
        try {
            path = convert(path);
            URL keyFile =
                ClassLoader.getSystemResource(path);
            return new InputStreamReader(keyFile.openStream());
            
        } catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    public static Reader getFromCache(String path) {
        path = CACHE_NAME + convert(path);
        try {
            return new InputStreamReader(ClassLoader.getSystemResource(path).openStream());
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    
    public static void saveOnCache(String fileName, Reader reader) {
        fileName =  convert(fileName);
        try (PrintWriter writer = new PrintWriter(CACHE_PATH +fileName)) {
            reader.transferTo(writer);
        }
        catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }
}
