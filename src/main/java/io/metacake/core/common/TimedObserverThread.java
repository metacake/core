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
    private Runnable runnable;
    private long milliTimerTime;

    /**
     * @param target The runnable to run each loop
     * @param window The window to attach to
     */
    public TimedObserverThread(Runnable target, CakeWindow window) {
        this(target, window, DEFAULT_THREAD_TIMER_TIME);
    }

    /**
     * @param target The runnable to run each loop
     * @param window The window to attach to
     * @param milliTimerTime The milli seconds between each loop. <p>see MilliTimer for detail</p>
     */
    public TimedObserverThread(Runnable target, CakeWindow window, long milliTimerTime) {
        this.runnable = target;
        window.addCloseObserver(new ThreadObserver(this));
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
    private void requestStop() {
        running = false;
        this.interrupt();
    }

    /**
     * Shuts down the given ThreadObserverThread when the #onClose method is called.
     */
    private static class ThreadObserver implements CloseObserver {
        private TimedObserverThread thread;

        ThreadObserver(TimedObserverThread thread) {
            this.thread = thread;
        }

        @Override
        public void onClose() {
            thread.requestStop();
        }
    }
}