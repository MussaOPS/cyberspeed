package com.scratch.game.domain;

import java.util.List;
import java.util.Map;

public class WinningCombinations {

    Map<String, List<String>> appliedWinningCombinations;
    Map<String, Double> symbolRewards;

    public WinningCombinations(Map<String, List<String>> appliedWinningCombinations, Map<String, Double> symbolRewards) {
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.symbolRewards = symbolRewards;
    }

    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public Map<String, Double> getSymbolRewards() {
        return symbolRewards;
    }
}
