package Utilities;

import java.util.Random;

public enum RandomNumber {
    INSTANCE;
    private long seed = 7;
    private Random num = new Random(7);

    /**
     * makeNumberBelowMax(int maxRange)
     * creates random double what is below a certain int value
     * @param maxRange
     * @return randomDouble
     */
    public double makeNumberBelowMax(int maxRange) {
        double randomDouble = maxRange * num.nextDouble();
        return randomDouble;
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
        double randomDouble = min + makeNumberBelowMax(range);
        return randomDouble;
    }

    /**
     * setSeed(long value)
     * sets seed of Random Number Generator
     * @param value
     */
    public void setSeed(long value) {
        num.setSeed(value);
        seed = value;
    }
}
