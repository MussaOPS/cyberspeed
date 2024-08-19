package com.scratch.game.service.impl;

import com.scratch.game.domain.GameConfig;
import com.scratch.game.domain.WinningCombinations;
import com.scratch.game.enums.WinCombination;
import com.scratch.game.service.WinningEvaluatorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinningEvaluatorServiceImpl implements WinningEvaluatorService {

    @Override
    public WinningCombinations evaluateWinningCombinations(GameConfig gameConfig, String[][] matrix) {

        Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
        Map<String, Double> symbolRewards = new HashMap<>();

        for (Map.Entry<String, GameConfig.WinCombination> entry : gameConfig.winCombinations.entrySet()) {
            GameConfig.WinCombination winCombination = entry.getValue();

            if (this.checkWinningCombination(matrix, winCombination, symbolRewards, gameConfig.symbols)) {
                for (String symbol : symbolRewards.keySet()) {
                    appliedWinningCombinations
                            .computeIfAbsent(symbol, k -> new ArrayList<>())
                            .add(winCombination.when);
                }
            }
        }

        return new WinningCombinations(appliedWinningCombinations, symbolRewards);
    }

    private boolean checkWinningCombination(
            String[][] matrix,
            GameConfig.WinCombination winCombination,
            Map<String, Double> symbolRewards,
            Map<String, GameConfig.Symbol> symbolsConfig
    ) {

        if (WinCombination.SAME_SYMBOLS.getValue().equals(winCombination.when)) {
            return this.checkSameSymbols(matrix, winCombination, symbolRewards, symbolsConfig);
        } else if (WinCombination.LINEAR_SYMBOLS.getValue().equals(winCombination.when)) {
            return this.checkLinearSymbols(matrix, winCombination, symbolRewards, symbolsConfig);
        }

        return false;
    }

    private boolean checkSameSymbols(
            String[][] matrix,
            GameConfig.WinCombination winCombination,
            Map<String, Double> symbolRewards,
            Map<String, GameConfig.Symbol> symbolsConfig
    ) {

        Map<String, Integer> symbolCount = new HashMap<>();
        boolean anyWin = false;

        for (String[] strings : matrix) {
            for (String symbol : strings) {
                if (symbolsConfig.containsKey(symbol)) {
                    symbolCount.put(symbol, symbolCount.getOrDefault(symbol, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : symbolCount.entrySet()) {
            if (entry.getValue() >= winCombination.count) {
                symbolRewards.put(entry.getKey(), winCombination.rewardMultiplier * symbolRewards.getOrDefault(entry.getKey(), 1.0));
                anyWin = true;
            }
        }

        return anyWin;
    }

    private boolean checkLinearSymbols(
            String[][] matrix,
            GameConfig.WinCombination winCombination,
            Map<String, Double> symbolRewards,
            Map<String, GameConfig.Symbol> symbolsConfig
    ) {

        boolean anyWin = false;

        for (List<String> area : winCombination.coveredAreas) {
            String firstSymbol = null;
            boolean match = true;

            for (String position : area) {
                String[] parts = position.split(":");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                if (firstSymbol == null) {
                    firstSymbol = matrix[row][col];
                } else if (!matrix[row][col].equals(firstSymbol)) {
                    match = false;
                    break;
                }
            }

            if (match && symbolsConfig.containsKey(firstSymbol)) {
                symbolRewards.put(firstSymbol, winCombination.rewardMultiplier * symbolRewards.getOrDefault(firstSymbol, 1.0));
                anyWin = true;
            }
        }

        return anyWin;
    }
}
