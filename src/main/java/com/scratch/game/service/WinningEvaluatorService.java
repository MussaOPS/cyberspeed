package com.scratch.game.service;

import com.scratch.game.domain.GameConfig;
import com.scratch.game.domain.WinningCombinations;

public interface WinningEvaluatorService {

    WinningCombinations evaluateWinningCombinations(GameConfig gameConfig, String[][] matrix);
}
