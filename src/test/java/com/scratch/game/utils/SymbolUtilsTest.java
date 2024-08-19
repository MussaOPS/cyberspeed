package com.scratch.game.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SymbolUtilsTest {

    @Test
    void testGetRandomSymbol_returnsExpectedSymbol() {

        Map<String, Integer> symbols = new HashMap<>();
        symbols.put("A", 5);
        symbols.put("B", 3);
        symbols.put("C", 2);

        String symbol = SymbolUtils.getRandomSymbol(symbols);

        assertTrue(symbols.containsKey(symbol), "Returned symbol should be one of the expected symbols");
    }

    @Test
    void testGetRandomSymbol_withEmptyMap_returnsNull() {

        Map<String, Integer> symbols = new HashMap<>();

        String symbol = SymbolUtils.getRandomSymbol(symbols);

        assertNull(symbol, "When the map is empty, the method should return null.");
    }

    @Test
    void testGetRandomSymbol_withSingleEntry_returnsOnlyEntry() {

        Map<String, Integer> symbols = new HashMap<>();
        symbols.put("A", 1);

        String symbol = SymbolUtils.getRandomSymbol(symbols);

        assertEquals("A", symbol, "When only one symbol is present, it should be returned.");
    }

    @Test
    void testGetRandomSymbol_withAllZeroValues_returnsNull() {

        Map<String, Integer> symbols = new HashMap<>();
        symbols.put("A", 0);
        symbols.put("B", 0);
        symbols.put("C", 0);

        String symbol = SymbolUtils.getRandomSymbol(symbols);

        assertNull(symbol, "When all values are zero, the method should return null.");
    }

    @Test
    void testGetRandomSymbol_withOneLargeValue_returnsThatSymbol() {

        Map<String, Integer> symbols = new HashMap<>();
        symbols.put("A", 100000);
        symbols.put("B", 1);
        symbols.put("C", 1);

        String symbol = SymbolUtils.getRandomSymbol(symbols);

        assertEquals("A", symbol, "When one symbol has a much larger value, it should be returned most of the time.");
    }

    @Test
    void testGetRandomSymbol_probabilityDistributionIsRespected() {

        Map<String, Integer> symbols = new HashMap<>();
        symbols.put("A", 8);
        symbols.put("B", 2);

        int countA = 0;
        int countB = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            String symbol = SymbolUtils.getRandomSymbol(symbols);
            if ("A".equals(symbol)) {
                countA++;
            } else if ("B".equals(symbol)) {
                countB++;
            }
        }

        assertTrue(countA > countB, "Symbol 'A' should be returned more frequently than 'B'.");
        assertTrue(countA > iterations * 0.6, "Symbol 'A' should be returned around 80% of the time.");
        assertTrue(countB < iterations * 0.4, "Symbol 'B' should be returned around 20% of the time.");
    }
}
