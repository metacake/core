package io.metacake.core.common;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;

/**
 * The TimedObserverThread is a thread meant to execute Runnable code on a set time step.
 *
 * @author florence
 * @author rpless
 */
public class TimedObserverThread extends Thread {
    public static final long DEFAULT_THREAD_TIMER_TIME = 20;
    private volatile boolean running = true;
    private Runnable runnable;
    private long milliTimerTime;

    /**
     * @param target The runnable to run each loop
     */
    public TimedObserverThread(Runnable target) {
        this(target, DEFAULT_THREAD_TIMER_TIME);
    }

    /**
     * @param target The runnable to run each loop
     * @param milliTimerTime The milli seconds between each loop. <p>see MilliTimer for detail</p>
     */
    public TimedObserverThread(Runnable target, long milliTimerTime) {
        this.runnable = target;
        this.milliTimerTime = milliTimerTime;
    }

    @Override
    public void run() {
        MilliTimer timer = new MilliTimer(milliTimerTime);
        while (running) {
            timer.update();
            runnable.run();
            timer.block();
        }
    }

    /**
     *  Stops the thread.
     */
    public void requestStop() {
        running = false;
        this.interrupt();
    }
}