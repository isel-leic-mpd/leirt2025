package pt.isel.mpd.completable_futures_intro.nio2;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.mpd.completable_futures_intro.weather4.OpenWeatherWebApi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static pt.isel.mpd.completable_futures_intro.nio2.NioUtils.*;
import static pt.isel.mpd.completable_futures_intro.utils.ThreadUtils.sleep;


public class NioUtilsTests {
    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;
    
    private final static double PORTO_LAT  =  41.1494512;
    private final static double PORTO_LONG = -8.6107884;
    
    private static Logger logger = LoggerFactory.getLogger(NioUtilsTests.class);
    
   
    private CompletableFuture<String>
    getWeatherForLocal(double lat, double lon)  throws IOException{
        var channel = AsynchronousSocketChannel.open();
        var address = new InetSocketAddress(OpenWeatherWebApi.WEATHER_HOST, 80);
        var reqString =
            String.format("GET %s HTTP/1.1\r\nConnection:close\r\nHost:%s\r\n\r\n",
                String.format(OpenWeatherWebApi.WEATHER_AT_TEMPLATE, lat, lon),
                OpenWeatherWebApi.WEATHER_HOST);
        
        return connect(channel, address)
            .thenCompose( __ -> {
                logger.info("connect done, now send the request");
                return write(channel, reqString);
            })
            .thenCompose( __ -> {
                logger.info("send done, now receive the response");
                return read(channel);
            });
          
    }
    
    @Test
    public void invokeOpenWeatherAPIUsingNio2() throws IOException {
       
        getWeatherForLocal(LISBON_LAT, LISBON_LONG)
            .thenAccept(response-> {
                logger.info("response received, show it!");
                System.out.println(response);
            })
            .join();
    }
    
    
    @Test
    public void getWeatherInParallelUsingNio2() throws IOException {
        logger.info("start requests");
        var fl = getWeatherForLocal(LISBON_LAT, LISBON_LONG)
            .thenAcceptAsync(response-> {
                sleep(2000);
                logger.info("response received, show it!");
                System.out.println(response);
            });
       
        var fp = getWeatherForLocal(PORTO_LAT, PORTO_LONG)
                     .thenAcceptAsync(response-> {
                         sleep(2000);
                         logger.info("response received, show it!");
                         System.out.println(response);
                     });
        
        fl.join();
        fp.join();
        logger.info("end test");
    }
}
