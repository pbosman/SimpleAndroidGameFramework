package outhousedev.simpleandroidgameframework.simpleandroidgameframeworklib;

/**
 * Utility class implementing a simple stopwatch.
 */
public class Stopwatch {

    private long start;

    /**
     * Create the stopwatch object.
     */
    public Stopwatch() {
        reset();
    }

    /**
     * Reset the stopwatch.
     */
    public void reset() {
        start = System.currentTimeMillis();
    }

    /**
     * Return elapsed time in seconds since this object was created.
     * @return The elapsed time in seconds.
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }
}
