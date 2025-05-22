package pt.isel.mpd.completable_futures;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.CompletableFuture;

import static pt.isel.mpd.completable_futures.utils.ThreadUtils.sleep;


public class FailureTests {
    private static Logger logger =
        LoggerFactory.getLogger(FailureTests.class);
    
    
    private CompletableFuture<Integer> inc(int i) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("inc start");
            sleep(2000);
            if (i < 0) throw new RuntimeException("bad inc value");
            logger.info("inc end");
            return i + 1;
        });
    }
    
    @Test
    public void testCFChainOnError() {
        var cf = inc(-1)
            .thenApply(i -> {
                logger.info("on thenApply continuation");
                return i*i;
            });
        logger.info("before join");
        try {
            logger.info("result = " + cf.join());
        }
        catch(Exception e) {
            logger.info("error: " + e);
        }
        sleep(3000);
    }
    
    @Test
    public void testCFChainOnErrorWithRecover() {
        var cf =
            inc(-1).thenApply(i -> {
                logger.info("on thenApply continuation");
                return i*i;
            })
//            .exceptionally(err -> {
//                logger.info("exceptionally continuation");
//                return 0;
//            })
            .whenComplete((i, e) -> {
                logger.info("on whenComplete continuation");
                if (e != null) {
                    logger.info("complete with error: " + e);
                }
                else {
                    logger.info("complete with success: " + i);
                }
            });
        logger.info("before join");
        try {
            logger.info("result = " + cf.join());
        }
        catch(Exception e) {
            logger.info("error: " + e);
        }
    }
    
}
