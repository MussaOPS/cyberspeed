package com.scratch.game.service;

import com.scratch.game.domain.GameConfig;
import com.scratch.game.service.impl.MatrixFillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixFillServiceTest {

    private MatrixFillServiceImpl matrixFillService;
    private GameConfig gameConfig;

    @BeforeEach
    void setUp() {

        matrixFillService = new MatrixFillServiceImpl();

        gameConfig = new GameConfig();
        gameConfig.rows = 3;
        gameConfig.columns = 3;

        Map<String, GameConfig.Symbol> symbols = new HashMap<>();
        GameConfig.Symbol symbolA = new GameConfig.Symbol();
        symbolA.rewardMultiplier = 1.0;
        symbols.put("A", symbolA);

        gameConfig.symbols = symbols;

        gameConfig.probabilities = new GameConfig.Probabilities();

        GameConfig.Probabilities.StandardSymbol standardSymbol = new GameConfig.Probabilities.StandardSymbol();
        standardSymbol.column = 0;
        standardSymbol.row = 0;
        standardSymbol.symbols = new HashMap<>();
        standardSymbol.symbols.put("A", 100);

        gameConfig.probabilities.standardSymbols = new java.util.ArrayList<>();
        gameConfig.probabilities.standardSymbols.add(standardSymbol);

        gameConfig.probabilities.bonusSymbols = new GameConfig.Probabilities.BonusSymbols();
        gameConfig.probabilities.bonusSymbols.symbols = new HashMap<>();
        gameConfig.probabilities.bonusSymbols.symbols.put("BONUS", 100);
    }

    @Test
    void testFillMatrixWithStandardSymbols_createsCorrectMatrix() {

        String[][] matrix = matrixFillService.fillMatrixWithStandardSymbols(gameConfig);

        assertNotNull(matrix);
        assertEquals(3, matrix.length, "Matrix should have the correct number of rows.");
        assertEquals(3, matrix[0].length, "Matrix should have the correct number of columns.");
    }

    @Test
    void testFillMatrixWithBonusSymbol_appliesBonusCorrectly() {

        String[][] matrix = new String[3][3];
        String bonusSymbol = matrixFillService.fillMatrixWithBonusSymbol(matrix, gameConfig, 100);

        assertNotNull(bonusSymbol, "Bonus symbol should not be null when applied.");
        assertEquals("BONUS", bonusSymbol, "The applied bonus symbol should be 'BONUS'.");
    }

    @Test
    void testFillMatrixWithDifferentSizes_createsCorrectSizedMatrix() {

        gameConfig.rows = 5;
        gameConfig.columns = 4;

        String[][] matrix = matrixFillService.fillMatrixWithStandardSymbols(gameConfig);

        assertNotNull(matrix);
        assertEquals(5, matrix.length, "Matrix should have the correct number of rows.");
        assertEquals(4, matrix[0].length, "Matrix should have the correct number of columns.");
    }

    @Test
    void testFillMatrixWithNoBonusSymbol_returnsNull() {

        gameConfig.probabilities.bonusSymbols.symbols.clear();

        String[][] matrix = new String[3][3];
        String bonusSymbol = matrixFillService.fillMatrixWithBonusSymbol(matrix, gameConfig, 100);

        assertNull(bonusSymbol, "If no bonus symbols are available, the method should return null.");
    }

    @Test
    void testFillMatrixWithBonusSymbol_appliesBonusToCorrectPosition() {

        String[][] matrix = new String[3][3];
        matrixFillService.fillMatrixWithBonusSymbol(matrix, gameConfig, 100);

        boolean bonusApplied = false;

        for (String[] strings : matrix) {
            for (String string : strings) {
                if ("BONUS".equals(string)) {
                    bonusApplied = true;
                    break;
                }
            }
        }

        assertTrue(bonusApplied, "The bonus symbol should be applied to the matrix.");
    }

    @Test
    void testFillMatrixWithDifferentSymbols_createsMatrixWithDifferentSymbols() {

        GameConfig.Symbol symbolB = new GameConfig.Symbol();
        symbolB.rewardMultiplier = 2.0;
        gameConfig.symbols.put("B", symbolB);

        GameConfig.Probabilities.StandardSymbol standardSymbol = new GameConfig.Probabilities.StandardSymbol();
        standardSymbol.column = 1;
        standardSymbol.row = 1;
        standardSymbol.symbols = new HashMap<>();
        standardSymbol.symbols.put("A", 50);
        standardSymbol.symbols.put("B", 50);

        gameConfig.probabilities.standardSymbols.add(standardSymbol);

        String[][] matrix = matrixFillService.fillMatrixWithStandardSymbols(gameConfig);

        assertNotNull(matrix);
        boolean containsA = false;
        boolean containsB = false;

        for (String[] strings : matrix) {
            for (String string : strings) {
                if ("A".equals(string)) {
                    containsA = true;
                }
                if ("B".equals(string)) {
                    containsB = true;
                }
            }
        }

        assertTrue(containsA, "Matrix should contain symbol 'A'.");
        assertTrue(containsB, "Matrix should contain symbol 'B'.");
    }

    @Test
    void testFillMatrixWithStandardSymbols_noProbabilitiesSet_returnsEmptyMatrix() {

        gameConfig.probabilities.standardSymbols.clear();

        String[][] matrix = matrixFillService.fillMatrixWithStandardSymbols(gameConfig);

        for (String[] strings : matrix) {
            for (String string : strings) {
                assertNull(string, "When no probabilities are set, the matrix should be empty.");
            }
        }
    }
}
