package com.scratch.game.utils;

import java.util.Map;
import java.util.Random;

public class SymbolUtils {

    public static String getRandomSymbol(Map<String, Integer> symbols) {

        if (symbols.isEmpty()) {
            return null;
        }

        Random random = new Random();

        int total = symbols.values().stream().mapToInt(Integer::intValue).sum();

        if (total <= 0) {
            return null;
        }

        int randomNumber = random.nextInt(total);

        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            randomNumber -= entry.getValue();
            if (randomNumber < 0) {
                return entry.getKey();
            }
        }

        return symbols.keySet().iterator().next();
    }
}
