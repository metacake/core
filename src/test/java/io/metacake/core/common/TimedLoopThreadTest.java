package io.metacake.core.common;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author florence
 * @author rpless
 */
public class TimedLoopThreadTest {
    @Test
    public void testRequestShutdownBlocks() throws Exception {
        TimedLoopThread t = new TimedLoopThread(() ->  {
            for(int i = 0; i < 100; i += 1){
                Math.cos(Math.random());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        });
        t.start();
        Thread.sleep(5);
        t.requestStop();
        assertFalse(t.isAlive());
    }
}
