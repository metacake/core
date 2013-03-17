package io.metacake.core.common;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;

/**
 * This class is a thread meant to run update loops for things like drawing.
 * The thread is attached to a window, and will shutdown with that window
 *
 * @author florence
 * @author rpless
 */
public class TimedObserverThread extends Thread {
    public static final long DEFAULT_THREAD_TIMER_TIME = 20;
    volatile boolean running = true;
    Runnable r;
    long milliTimerTime;

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
     *  Stops thread. <p>Stupid name cause Thread#stop is final -.-</p>
     */
    private void stopReal() {
        running = false;
        this.interrupt();
    }

    /**
     * And observer the window to shutdown threads on close
     */
    class ThreadObserver implements CloseObserver {
        TimedObserverThread t;

        ThreadObserver(TimedObserverThread t) {
            this.t = t;
        }

        @Override
        public void onClose() {
            t.stopReal();
        }
    }
}
