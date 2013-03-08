package io.metacake.core.common;

/**
 * This class acts as a Microsecond timer.
 * It has to functions. The first is to record the difference between two times.
 * The second is to act as a 'time mechanism', blocking a thread until a give time.
 * The accuracy of this time is approximately the accuracy of System.nanoTime()
 * on the give system
 *
 * @author Spencer Florence
 */
public class MilliTimer {
    private long interval = 0;
    private long lastActive = System.nanoTime();
    private final long TO_MILLIS = 1000000;

    /**
     * Constructs a timer with a 0 interval
     */
    public MilliTimer() {
    }

    /**
     * Constructs a timer with the given interval
     *
     * @param interval The interval for this timer
     */
    public MilliTimer(long interval) {
        this.interval = interval;
    }

    /**
     * Set the timing interval in milliseconds
     *
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
            if (waitFor > 0)
                Thread.sleep(waitFor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the number of milliseconds since the last call to update
     *
     * @return the number of milliseconds
     */
    public long poll() {
        return (System.nanoTime() - lastActive) / TO_MILLIS;
    }

    /**
     * polls the timer then updates the internal time
     */
    public long update() {
        long ret = this.poll();
        this.lastActive = System.nanoTime();
        return ret;
    }

    /**
     * {@code poll()}s the timer, and returns true if {@code interval} milliseconds has passed since the last poll
     */
    public boolean intervalPassed() {
        return this.poll() > this.interval;
    }
}

