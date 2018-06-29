package io.github.inugroho.crashtestdummy.util;

import java.util.Random;

public class ThreadedRandom {
  private static ThreadLocal<Random> randomGenerator = null;

  public static void seed(Long seed) {
    initialize(seed);
  }

  private static void initialize(Long seed) {
    randomGenerator = ThreadLocal.withInitial(() -> (seed == null) ? new Random() : new Random(seed));
  }

  public static int nextInt() {
    if (randomGenerator == null) {
      initialize(null);
    }
    return randomGenerator.get().nextInt();
  }

  public static int nextInt(int upperBound) {
    if (randomGenerator == null) {
      initialize(null);
    }
    return randomGenerator.get().nextInt(upperBound);

  }

  public static int nextInt(int lowerBound, int upperBound) {
    if (randomGenerator == null) {
      initialize(null);
    }
    return randomGenerator.get().nextInt(upperBound) + lowerBound;
  }

  public static long nextLong() {
    if (randomGenerator == null) {
      initialize(null);
    }
    return randomGenerator.get().nextLong();
  }

  public static long nextLong(long upperBound) {
    if (randomGenerator == null) {
      initialize(null);
    }
    if (upperBound < Integer.MAX_VALUE) {
      return (long) randomGenerator.get().nextInt((int) upperBound);
    }
    Random r = randomGenerator.get();
    long v = r.nextLong();
    while (v >= upperBound) {
      v = r.nextLong();
    }
    return v;

  }

  public static long nextLong(long lowerBound, long upperBound) {
    if (randomGenerator == null) {
      initialize(null);
    }
    long range = upperBound - lowerBound;
    if (range < 0) {
      range = -range;
      lowerBound = upperBound;
    }
    if (range < Integer.MAX_VALUE) {
      return lowerBound + (long) randomGenerator.get().nextInt((int) range);
    }

    Random r = randomGenerator.get();
    long v = r.nextLong();
    while (v >= range) {
      v = r.nextLong();
    }
    return lowerBound + v;

  }

}
