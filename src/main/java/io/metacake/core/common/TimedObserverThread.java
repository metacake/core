package io.metacake.core.common;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;

/**
 * The TimedObserverThread is a thread meant to execute Runnable code as long as the given CakeWindow has not shutdown.
 * <p>
 * The thread is attached to a window, and will shutdown with that window.
 *
 * @author florence
 * @author rpless
 */
public class TimedObserverThread extends Thread {
    public static final long DEFAULT_THREAD_TIMER_TIME = 20;
    private volatile boolean running = true;
    private Runnable r;
    private long milliTimerTime;

    /**
     * @param target The runnable to run each loop
     * @param w The window to attach to
     */
    public TimedObserverThread(Runnable target, CakeWindow w) {
        this(target, w, DEFAULT_THREAD_TIMER_TIME);
    }

    /**
     * @param target The runnable to run each loop
     * @param w The window to attach to
     * @param milliTimerTime The milli seconds between each loop. <p>see MilliTimer for detail</p>
     */
    public TimedObserverThread(Runnable target, CakeWindow w, long milliTimerTime) {
        this.r = target;
        w.addCloseObserver(new ThreadObserver(this));
        this.milliTimerTime = milliTimerTime;
    }

    @Override
    public void run() {
        MilliTimer t = new MilliTimer(milliTimerTime);
        while (running) {
            t.update();
            r.run();
            t.block();
        }
    }

    /**
     *  Stops the thread.
     */
    private void requestStop() {
        running = false;
        this.interrupt();
    }

    /**
     * Shuts down the given ThreadObserverThread when the #onClose method is called.
     */
    private static class ThreadObserver implements CloseObserver {
        TimedObserverThread t;

        ThreadObserver(TimedObserverThread t) {
            this.t = t;
        }

        @Override
        public void onClose() {
            t.requestStop();
        }
    }
}