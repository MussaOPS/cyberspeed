package com.scratch.game.domain;

import java.util.List;
import java.util.Map;

public class GameResult {

    public String[][] matrix;
    public double reward;
    public Map<String, List<String>> appliedWinningCombinations;
    public String appliedBonusSymbol;

    public GameResult(String[][] matrix, double reward, Map<String, List<String>> appliedWinningCombinations, String appliedBonusSymbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }
}
