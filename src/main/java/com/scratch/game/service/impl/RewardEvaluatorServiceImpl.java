package com.scratch.game.service.impl;

import com.scratch.game.domain.GameConfig;
import com.scratch.game.enums.BonusImpact;
import com.scratch.game.service.RewardEvaluatorService;

import java.util.Map;

public class RewardEvaluatorServiceImpl implements RewardEvaluatorService {

    @Override
    public double evaluateStandardReward(Map<String, Double> symbolRewards, GameConfig gameConfig) {

        double reward = 0;

        for (Map.Entry<String, Double> entry : symbolRewards.entrySet()) {
            String symbol = entry.getKey();
            double rewardMultiplier = entry.getValue();
            double symbolReward = 100 * gameConfig.symbols.get(symbol).rewardMultiplier * rewardMultiplier;
            reward += symbolReward;
        }

        return reward;
    }

    @Override
    public double evaluateBonusReward(Map<String, GameConfig.Symbol> symbolsConfig, String appliedBonusSymbol, double reward) {

        if (appliedBonusSymbol == null) return reward;

        GameConfig.Symbol bonusConfig = symbolsConfig.get(appliedBonusSymbol);

        if (bonusConfig != null && BonusImpact.MULTIPLY_REWARD.getValue().equals(bonusConfig.impact)) {
            return reward * bonusConfig.rewardMultiplier;
        } else if (bonusConfig != null && BonusImpact.EXTRA_BONUS.getValue().equals(bonusConfig.impact)) {
            return reward + bonusConfig.extra;
        }

        return reward;
    }
}
