package com.scratch.game.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class GameConfig {

    public int columns;
    public int rows;
    public Map<String, Symbol> symbols;
    public Probabilities probabilities;
    @JsonProperty("win_combinations")
    public Map<String, WinCombination> winCombinations;

    public static class Symbol {

        @JsonProperty("reward_multiplier")
        public double rewardMultiplier;
        public String type;
        public double extra;
        public String impact;
    }

    public static class Probabilities {

        @JsonProperty("standard_symbols")
        public List<StandardSymbol> standardSymbols;
        @JsonProperty("bonus_symbols")
        public BonusSymbols bonusSymbols;

        public static class StandardSymbol {

            public int column;
            public int row;
            public Map<String, Integer> symbols;
        }

        public static class BonusSymbols {

            public Map<String, Integer> symbols;
        }
    }

    public static class WinCombination {

        @JsonProperty("reward_multiplier")
        public double rewardMultiplier;
        public String when;
        public int count;
        public String group;
        @JsonProperty("covered_areas")
        public List<List<String>> coveredAreas;
    }
}
