package pt.isel.mpd.completable_futures;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mpd.completable_futures.utils.ThreadUtils.sleep;

public class SerialChainTests {
    private static Logger logger =
        LoggerFactory.getLogger(FailureTests.class);
    
    
    private CompletableFuture<Integer> sub(int i1, int i2) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("sub start");
            sleep(2000);
          
            logger.info("sub end");
            return i1 - i2;
        });
    }
    
    
    private CompletableFuture<Integer> subAllRec(List<Integer> values, int acc) {
        if (values.isEmpty()) {
            return CompletableFuture.completedFuture(acc);
        }
        else {
             return sub(acc, values.getFirst())
            .thenCompose( res -> subAllRec(values.subList(1, values.size()), res))
            .whenComplete((i, exc) -> {
                if (exc != null) {
                    logger.info("completed with error: " + exc);
                }
                else {
                    logger.info("completed with value from: " + i + ", " + acc);
                }
            });
        }
    }
    
    
    private CompletableFuture<Integer> subAll(List<Integer> values) {
       if (values.isEmpty()) throw new IllegalArgumentException("List can't be empty");
       var first = values.getFirst();
       logger.info("before subAllRec call");
       var res = subAllRec(values.subList(1, values.size()), first);
       logger.info("after subAllRec call");
       return res;
    }
    
   
    
    @Test
    public void subAllTest()  {
        var values = List.of(3, -6  , 4 , -1);
        
        logger.info("before subAll call");
        var cf = subAll(values);
        logger.info("after subAll call");
        
        assertEquals(6,cf.join() );
        logger.info("test done");
        
    }
}
