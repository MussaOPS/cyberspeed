package com.scratch.game.service;

import com.scratch.game.domain.GameConfig;

import java.util.Map;

public interface RewardEvaluatorService {

    double evaluateStandardReward(Map<String, Double> symbolRewards, GameConfig gameConfig);

    double evaluateBonusReward(Map<String, GameConfig.Symbol> symbolsConfig, String appliedBonusSymbol, double reward);
}
