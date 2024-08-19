package com.scratch.game.service;

import com.scratch.game.domain.GameConfig;
import com.scratch.game.service.impl.RewardEvaluatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardEvaluatorServiceTest {

    private RewardEvaluatorServiceImpl rewardEvaluatorService;
    private GameConfig gameConfig;

    @BeforeEach
    void setUp() {

        rewardEvaluatorService = new RewardEvaluatorServiceImpl();

        gameConfig = new GameConfig();
        Map<String, GameConfig.Symbol> symbols = new HashMap<>();

        GameConfig.Symbol symbolA = new GameConfig.Symbol();
        symbolA.rewardMultiplier = 1.0;
        symbols.put("A", symbolA);

        GameConfig.Symbol bonusSymbol = new GameConfig.Symbol();
        bonusSymbol.rewardMultiplier = 2.0;
        bonusSymbol.impact = "multiply_reward";
        symbols.put("BONUS", bonusSymbol);

        GameConfig.Symbol extraBonusSymbol = new GameConfig.Symbol();
        extraBonusSymbol.extra = 50.0;
        extraBonusSymbol.impact = "extra_bonus";
        symbols.put("EXTRA_BONUS", extraBonusSymbol);

        gameConfig.symbols = symbols;
    }

    @Test
    void testEvaluateStandardReward_returnsExpectedReward() {

        Map<String, Double> symbolRewards = new HashMap<>();
        symbolRewards.put("A", 1.0);

        double reward = rewardEvaluatorService.evaluateStandardReward(symbolRewards, gameConfig);
        assertEquals(100.0, reward, 0.01, "Reward should be correctly calculated.");
    }

    @Test
    void testEvaluateBonusReward_withBonusMultiplier_returnsMultipliedReward() {

        double reward = 100.0;
        double totalReward = rewardEvaluatorService.evaluateBonusReward(gameConfig.symbols, "BONUS", reward);

        assertEquals(200.0, totalReward, "Bonus reward should be multiplied correctly.");
    }

    @Test
    void testEvaluateBonusReward_withExtraBonus_returnsIncreasedReward() {

        double reward = 100.0;
        double totalReward = rewardEvaluatorService.evaluateBonusReward(gameConfig.symbols, "EXTRA_BONUS", reward);

        assertEquals(150.0, totalReward, "Bonus reward should be increased by the extra amount correctly.");
    }

    @Test
    void testEvaluateBonusReward_withNullBonusSymbol_returnsOriginalReward() {

        double reward = 100.0;
        double totalReward = rewardEvaluatorService.evaluateBonusReward(gameConfig.symbols, null, reward);

        assertEquals(100.0, totalReward, "If no bonus symbol is applied, the reward should remain unchanged.");
    }

    @Test
    void testEvaluateBonusReward_withUnknownBonusSymbol_returnsOriginalReward() {

        double reward = 100.0;
        double totalReward = rewardEvaluatorService.evaluateBonusReward(gameConfig.symbols, "UNKNOWN", reward);

        assertEquals(100.0, totalReward, "If the bonus symbol is unknown, the reward should remain unchanged.");
    }

    @Test
    void testEvaluateBonusReward_withNoImpact_returnsOriginalReward() {

        GameConfig.Symbol neutralSymbol = new GameConfig.Symbol();
        neutralSymbol.rewardMultiplier = 1.0;
        gameConfig.symbols.put("NEUTRAL", neutralSymbol);

        double reward = 100.0;
        double totalReward = rewardEvaluatorService.evaluateBonusReward(gameConfig.symbols, "NEUTRAL", reward);

        assertEquals(100.0, totalReward, "If the bonus symbol has no impact, the reward should remain unchanged.");
    }

    @Test
    void testEvaluateStandardReward_withEmptySymbolRewards_returnsZeroReward() {

        Map<String, Double> symbolRewards = new HashMap<>();

        double reward = rewardEvaluatorService.evaluateStandardReward(symbolRewards, gameConfig);

        assertEquals(0.0, reward, 0.01, "If no symbols are provided, the reward should be zero.");
    }

    @Test
    void testEvaluateBonusReward_withBonusMultiplierAndNoBaseReward_returnsZeroReward() {

        double reward = 0.0;
        double totalReward = rewardEvaluatorService.evaluateBonusReward(gameConfig.symbols, "BONUS", reward);

        assertEquals(0.0, totalReward, "If the base reward is zero, the final reward should remain zero even with a bonus multiplier.");
    }
}
