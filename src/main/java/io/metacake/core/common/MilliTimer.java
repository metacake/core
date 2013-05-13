package io.metacake.core.common;

/**
 * This class acts as a Millisecond timer.
 * <p>
 * It has two functions. The first is to record the difference between two times.
 * The second is to act as a 'time mechanism', blocking a thread until a give time.
 * The accuracy of this time is approximately the accuracy of System.nanoTime()
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
     * Set the timing interval in milliseconds
     * @param interval the new interval
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * Block the current thread in such a way that {@code interval} milliseconds has occurred
     * since the last call to update. If greater than {@code interval}
     * milliseconds have passed, calling this has no effect.
     */
    public void block() {
        try {
            long waitFor = interval - this.poll();
            if (waitFor > 0) {
                Thread.sleep(waitFor);
            }
        } catch (InterruptedException e) {
            // e.printStackTrace(); // TODO: convert to logger (when we have it)
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Calling update polls the timer and updates the time that it was last active to be now.
     * @return polls the timer then updates the internal time
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