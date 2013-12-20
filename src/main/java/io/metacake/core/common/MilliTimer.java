package io.metacake.core.common;

/**
 * This class acts as a Millisecond timer.
 * <p>
 * It has two functions. The first is to record the difference between two times.
 * The second is to act as a 'time mechanism', blocking a thread until a give time.
 * The accuracy of this time is approximately the accuracy of (@link System#nanoTime}.
 * on the give system.
 *
 * @author florence
 */
public class MilliTimer {
    private static final long TO_MILLIS = 1_000_000;

    private long interval;
    private long lastActive;

    /**
     * Constructs a timer with a 0 interval.
     */
    public MilliTimer() {
        this(0);
    }

    /**
     * Constructs a timer with the given interval
     * @param interval The interval for this timer
     */
    public MilliTimer(long interval) {
        this.interval = interval;
        this.lastActive = System.nanoTime();
    }

    /**
     * Block the current thread in such a way that {@code interval} milliseconds has occurred
     * since the last call to update. If greater than {@code interval}
     * milliseconds have passed, calling this has no effect.
     * @throws InterruptedException thrown if the blocking operation is interrupted
     */
    public void block() throws InterruptedException {
        long waitFor = interval - this.poll();
        if (waitFor > 0) {
            Thread.sleep(waitFor);
        }
    }

    /**
     * Calling update polls the timer and updates the time that it was last active to be now.
     * Effect: Updates the the internal time that is being tracked by the timer.
     * @return returns the time since the last update in milliseconds
     */
    public long update() {
        long ret = this.poll();
        this.lastActive = System.nanoTime();
        return ret;
    }

    /**
     * This method only polls and does not mutate.
     * @return Gets the number of milliseconds since the last call to update
     */
    public long poll() {
        return (System.nanoTime() - lastActive) / TO_MILLIS;
    }

    /**
     * This method only polls and does not mutate.
     * @return returns true if {@code interval} milliseconds has passed since the last poll
     */
    public boolean intervalPassed() {
        return this.poll() > this.interval;
    }
}