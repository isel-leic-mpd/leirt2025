package pt.isel.mpd.completable_futures_intro.start;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.mpd.completable_futures_intro.weather4.OpenWeatherWebApi;
import pt.isel.mpd.completable_futures_intro.weather4.dto.WeatherInfoDto;
import pt.isel.mpd.completable_futures_intro.weather4.requests.HttpRequest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mpd.completable_futures_intro.utils.ThreadUtils.join;
import static pt.isel.mpd.completable_futures_intro.utils.ThreadUtils.sleep;

public class ThreadsAndCompletableFutureTests {
    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;
    
    private final static double PORTO_LAT  =  41.1494512;
    private final static double PORTO_LONG = -8.6107884;
    
    
    private static Logger logger =
        LoggerFactory.getLogger(ThreadsAndCompletableFutureTests.class);
    
    @Test
    public void firstExampleWithThreads() {
        logger.info("in test thread");
        Thread t = new Thread(() -> {
            sleep(1000);
            logger.info("Hello from new thread");
        });
        
        t.start();
        join(t);
    }
    
    // what can go wrong with this code?
    private List<WeatherInfoDto> getWeatherFromLisbonAndPortoInParallel() {
        var webApi = new OpenWeatherWebApi(new HttpRequest());
        var citiesWeather = new LinkedList<WeatherInfoDto>();
        
        var tLisbon = new Thread(() -> {
            var res = webApi.weatherAt(LISBON_LAT, LISBON_LONG);
            citiesWeather.add(res);
        });
        tLisbon.start();
        
        var tPorto = new Thread(() -> {
            var res = webApi.weatherAt(PORTO_LAT, PORTO_LONG);
            citiesWeather.add(res);
        });
        tPorto.start();
        
        join(tLisbon);
        join(tPorto);
        
        return citiesWeather;
    }
    
    
    @Test
    public void get_weather_at_lisbon_oporto_in_parallel_using_threads() {
        var weather =
            getWeatherFromLisbonAndPortoInParallel();
        assertEquals(2, weather.size());
        
        for(var wi : weather) {
            System.out.println(wi);
        }
    }
    
    
    /*-------------------------------------------------
     * CompletableFutures
     */
    
    private CompletableFuture<Integer> inc(int i) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("inc start");
            sleep(2000);
            logger.info("inc end");
            return i + 1;
        });
    }
    
    private CompletableFuture<Long> square(int i) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("square start");
            sleep(3000);
            logger.info("square end");
            return (long)i * i;
        });
    }
    
    private long squareSynch(int i) {
        return i*i;
    }
    
    @Test
    public void inc2NumbersInParallel() {
        // launch a completable future...
        CompletableFuture<Integer> f1 = inc(3);
        
        // launch another completable future...
        CompletableFuture<Integer> f2 = inc(2);
        
        // and wait for both... ugh!
        var f1Res = f1.join();
        var f2Res = f2.join();
        
        assertEquals(4, f1Res);
        assertEquals(3, f2Res);
    }
    
    @Test
    public void combineTwoCompletableFutures() {
        // launch a completable future...
        CompletableFuture<Integer> f1 =  inc(3);
        
        // launch another completable future...
        CompletableFuture<Integer> f2 = inc(4);
      
        // and combine them.. ok!
        CompletableFuture<Integer> fc = f1.thenCombine(f2, (i1, i2) -> i1 + i2);
        
        // since we are in a test we must wait anyway...
        assertEquals(9, fc.join());
    }
    
    @Test
    public void chainOfOperationsUsingFutures() {
        // squareSynch(inc(2))
        CompletableFuture<Long> cf =
            inc(2)
            .thenApply( i -> squareSynch(i));
        
        assertEquals(9L, cf.join());
        logger.info("test end");
    }
    
    @Test
    public void sequenceOfAsyncOperationsUsingFutures() {
        // square(inc(2))
        var cf =
            inc(2).thenCompose( i -> square(i));
        
        
        assertEquals(9L, cf.join());
        logger.info("test end");
    }
    
    /**
     * Produce a new list the results from aplying "inc" operation to all
     * integers in values
     *
     * Running the incAll0Test, explain the different behaviours between the uncommented
     * and the comment versions of the code below
     * @param values
     */
    private void incAll0(List<Integer> values) {
        
        var s = values.stream()
            .map( i -> inc(i));

        logger.info("stream produced");
        sleep(8000);
        logger.info("start foreach");
        s.forEach(f -> System.out.println(f.join()));
        logger.info("end foreach");
 
        
//        logger.info("stream produce");
//        var l = values.stream()
//                     .map( i -> inc(i))
//                    .toList();
//        logger.info("start foreach");
//        l.forEach(f -> System.out.println(f.join()));
//        logger.info("end foreach");
    }
    
    @SuppressWarnings("unchecked")
    private CompletableFuture<List<Integer>> incAll1(List<Integer> values) {
        CompletableFuture<Integer>[] futures =
             values.stream()
                   .map(i -> inc(i))
                   .toArray(size-> new CompletableFuture[size]);
        return  CompletableFuture.allOf(futures)
                .thenApply(__ -> Arrays.stream(futures).map(f -> f.join()).toList());
             
    }
    
    private CompletableFuture<Integer> incAndAddAll0(List<Integer> values) {
         // sum of increments
        CompletableFuture<Integer>[] futures =
            values.stream()
                .map(i -> inc(i))
                .toArray(size-> new CompletableFuture[size]);
        return  CompletableFuture.allOf(futures)
        .thenApply(__ -> Arrays.stream(futures)
                         .map(f -> f.join())
                         .reduce(0, (i1, i2) -> i1 + i2) );
    }
    
    private CompletableFuture<Integer> incAndAddAll(List<Integer> values) {
        // sum of increments
        CompletableFuture<Integer> initial = CompletableFuture.completedFuture(0);
        return
            values.stream()
            .map(i -> inc(i))
            .reduce(initial, (f1, f2) -> f1.thenCombine(f2, (i1, i2) -> i1 + i2));
    }
    
    
    // testes
    
    @Test
    public void incAll0Test() {
        var values = List.of(1,2,3,4);
        incAll0(values);
    }
    
    @Test
    public void incAll1Test() {
        logger.info("test start");
        var values = List.of(1,2,3,4);
        var expected = List.of(2,3,4,5);
        
        var futResult = incAll1(values);
        assertEquals(expected, futResult.join());
        logger.info("test end");
    }
    
    @Test
    public void incAndAddAllTest() {
        logger.info("test start");
        var values = List.of(1,2,3,4);
        var expected = 14;
        
        var futResult = incAndAddAll(values);
        assertEquals(expected, futResult.join());
        logger.info("test end");
    }
    
    @Test
    public void manualCompletionTest() {
        var cf = new CompletableFuture<Integer>();
        logger.info("start test");
        cf.thenApply(i -> {
            logger.info("in future thenApply, i = " + 3);
            return i;
        });
        
        sleep(2000);
        cf.complete(3);
    }
}
