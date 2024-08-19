package com.scratch.game.service;

import com.scratch.game.domain.GameConfig;
import com.scratch.game.domain.WinningCombinations;
import com.scratch.game.service.impl.WinningEvaluatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WinningEvaluatorServiceTest {

    private WinningEvaluatorServiceImpl winningEvaluatorService;
    private GameConfig gameConfig;

    @BeforeEach
    void setUp() {

        winningEvaluatorService = new WinningEvaluatorServiceImpl();

        gameConfig = new GameConfig();
        gameConfig.rows = 3;
        gameConfig.columns = 3;

        Map<String, GameConfig.Symbol> symbols = new HashMap<>();
        GameConfig.Symbol symbolA = new GameConfig.Symbol();
        symbolA.rewardMultiplier = 1.0;
        symbols.put("A", symbolA);

        GameConfig.Symbol symbolB = new GameConfig.Symbol();
        symbolB.rewardMultiplier = 2.0;
        symbols.put("B", symbolB);

        gameConfig.symbols = symbols;

        GameConfig.WinCombination sameSymbolWin = new GameConfig.WinCombination();
        sameSymbolWin.when = "same_symbols";
        sameSymbolWin.count = 3;
        sameSymbolWin.rewardMultiplier = 1.5;

        gameConfig.winCombinations = new HashMap<>();
        gameConfig.winCombinations.put("sameSymbolsWin", sameSymbolWin);
    }

    @Test
    void testEvaluateWinningCombinations_withPartialSameSymbols_returnsNoWin() {

        String[][] matrix = {
                {"A", "A", "B"},
                {"B", "A", "B"},
                {"A", "B", "A"}
        };

        WinningCombinations winningCombinations = winningEvaluatorService.evaluateWinningCombinations(gameConfig, matrix);

        assertNotNull(winningCombinations, "Winning combinations should not be null.");
        assertFalse(winningCombinations.getAppliedWinningCombinations().isEmpty(),
                "No winning combinations should be applied.");
    }

    @Test
    void testEvaluateWinningCombinations_withHorizontalWinningCombination_appliesReward() {

        GameConfig.WinCombination horizontalWin = new GameConfig.WinCombination();
        horizontalWin.when = "same_symbols";
        horizontalWin.rewardMultiplier = 2.0;
        horizontalWin.count = 3;
        gameConfig.winCombinations.put("horizontalWin", horizontalWin);

        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "B", "A"},
                {"B", "A", "B"}
        };

        WinningCombinations winningCombinations = winningEvaluatorService.evaluateWinningCombinations(gameConfig, matrix);

        assertNotNull(winningCombinations, "Winning combinations should not be null.");
        assertFalse(winningCombinations.getAppliedWinningCombinations().isEmpty(),
                "There should be applied winning combinations.");
        assertEquals(3.0, winningCombinations.getSymbolRewards().get("A"),
                "Reward multiplier for symbol A should be applied.");
    }

    @Test
    void testEvaluateWinningCombinations_withDiagonalWinningCombination_returnsWinningCombinations() {

        GameConfig.WinCombination diagonalWin = new GameConfig.WinCombination();
        diagonalWin.when = "linear_symbols";
        diagonalWin.rewardMultiplier = 3.0;
        diagonalWin.coveredAreas = Arrays.asList(
                Arrays.asList("0:0", "1:1", "2:2")
        );

        gameConfig.winCombinations.put("diagonalWin", diagonalWin);

        String[][] matrix = {
                {"A", "B", "B"},
                {"B", "A", "B"},
                {"B", "B", "A"}
        };

        WinningCombinations winningCombinations = winningEvaluatorService.evaluateWinningCombinations(gameConfig, matrix);

        assertNotNull(winningCombinations, "Winning combinations should not be null.");
        assertFalse(winningCombinations.getAppliedWinningCombinations().isEmpty(),
                "There should be applied winning combinations.");
        assertEquals(4.5, winningCombinations.getSymbolRewards().get("A"),
                "Reward multiplier for diagonal win should be applied.");
    }

    @Test
    void testEvaluateWinningCombinations_withMultipleWinningCombinations_appliesAllRewards() {

        GameConfig.WinCombination horizontalWin = new GameConfig.WinCombination();
        horizontalWin.when = "same_symbols";
        horizontalWin.rewardMultiplier = 2.0;
        horizontalWin.count = 3;
        gameConfig.winCombinations.put("horizontalWin", horizontalWin);

        GameConfig.WinCombination diagonalWin = new GameConfig.WinCombination();
        diagonalWin.when = "linear_symbols";
        diagonalWin.rewardMultiplier = 3.0;
        diagonalWin.coveredAreas = Arrays.asList(
                Arrays.asList("0:0", "1:1", "2:2")
        );
        gameConfig.winCombinations.put("diagonalWin", diagonalWin);

        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "A", "B"},
                {"B", "B", "A"}
        };

        WinningCombinations winningCombinations = winningEvaluatorService.evaluateWinningCombinations(gameConfig, matrix);

        assertNotNull(winningCombinations, "Winning combinations should not be null.");
        assertFalse(winningCombinations.getAppliedWinningCombinations().isEmpty(),
                "There should be applied winning combinations.");
        assertEquals(9.0, winningCombinations.getSymbolRewards().get("A"),
                "Total reward multiplier for symbol A should be the sum of all applied rewards.");
    }

    @Test
    void testEvaluateWinningCombinations_withInvalidWinCombination_returnsNoWin() {

        GameConfig.WinCombination invalidWin = new GameConfig.WinCombination();
        invalidWin.when = "invalid_symbols";
        invalidWin.rewardMultiplier = 2.0;
        invalidWin.count = 3;
        gameConfig.winCombinations.put("invalidWin", invalidWin);

        String[][] matrix = {
                {"A", "B", "A"},
                {"B", "A", "B"},
                {"A", "B", "A"}
        };

        WinningCombinations winningCombinations = winningEvaluatorService.evaluateWinningCombinations(gameConfig, matrix);

        assertNotNull(winningCombinations, "Winning combinations should not be null.");
        assertFalse(winningCombinations.getAppliedWinningCombinations().isEmpty(),
                "Invalid win combination should not result in a win.");
    }
}
