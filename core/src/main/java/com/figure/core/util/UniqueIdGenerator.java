package com.figure.core.util;

import java.util.Random;

public class UniqueIdGenerator {
    private static final Random random = new Random();

    public static long generateUniqueId() {
        long timestamp = System.nanoTime();
        int randomValue = random.nextInt(1000)+random.nextInt(1000);
        return timestamp - randomValue;
    }

    public static void main(String[] args) {
        System.out.println(generateUniqueId());
    }
}
