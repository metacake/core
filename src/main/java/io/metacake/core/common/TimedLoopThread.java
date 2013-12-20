package io.metacake.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TimedLoopThread is a thread meant to execute Runnable code on a set time step.
 *
 * @author florence
 * @author rpless
 */
public class TimedLoopThread extends Thread {
    public static final long DEFAULT_THREAD_TIMER_TIME = 20;
    private static Logger logger = LoggerFactory.getLogger(TimedLoopThread.class);
    private volatile boolean running = true;
    private Runnable runnable;
    private long milliTimerTime;

    /**
     * @param target The runnable to run each loop
     */
    public TimedLoopThread(Runnable target) {
        this(target, DEFAULT_THREAD_TIMER_TIME);
    }

    /**
     * @param target The runnable to run each loop
     * @param milliTimerTime The milli seconds between each loop. <p>see {@link MilliTimer} for more details.
     */
    public TimedLoopThread(Runnable target, long milliTimerTime) {
        this.runnable = target;
        this.milliTimerTime = milliTimerTime;
    }

    @Override
    public void run() {
        MilliTimer timer = new MilliTimer(milliTimerTime);
        while (running) {
            timer.update();
            runnable.run();
            try {
                timer.block();
            } catch (InterruptedException e) {
                running = false; // die on interrupts
            }
        }
    }

    /**
     *  Stops the thread. Blocks until the thread stops.
     *  DO NOT CALL THIS METHOD FROM WITHIN THE THREAD.
     */
    public void requestStop() {
        running = false;
        this.interrupt();
        try {
            this.join();
        } catch (InterruptedException e) {
            logger.debug("How the hell did this get interrupted?", e);
        }
    }
}