package Utilities;

import java.util.Random;

public enum RandomNumber {
    INSTANCE;
    private final long DEFAULT_SEED = 7;
    private Random num = new Random(DEFAULT_SEED);

    /**
     * makeNumberBelowMax(int maxRange)
     * creates random double what is below a certain int value
     * @param maxRange - highest random damage that should be calculated
     * @return randomDouble
     */
    public double makeNumberBelowMax(int maxRange) {
        return maxRange * num.nextDouble();
    }

    /**
     * makeNumberWithinRange(int max, int min)
     * creates a random double between two values
     * @param max
     * @param min
     * @return randomDouble
     */
    public double makeNumberWithinRange(int max, int min) {
        int range = max - min;
        return min + makeNumberBelowMax(range);
    }

    /**
     * setSeed(long value)
     * sets seed of Random Number Generator
     * @param value - seed for Random Number Generator
     */
    public void setSeed(long value) {
        num = new Random(value);
    }
}
