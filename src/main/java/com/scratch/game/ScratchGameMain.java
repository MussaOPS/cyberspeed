package com.scratch.game;

import com.scratch.game.domain.GameConfig;
import com.scratch.game.domain.GameResult;
import com.scratch.game.domain.WinningCombinations;
import com.scratch.game.errors.ScratchGameException;
import com.scratch.game.service.ArgumentsService;
import com.scratch.game.service.MatrixFillService;
import com.scratch.game.service.RewardEvaluatorService;
import com.scratch.game.service.WinningEvaluatorService;
import com.scratch.game.service.impl.ArgumentsServiceImpl;
import com.scratch.game.service.impl.MatrixFillServiceImpl;
import com.scratch.game.service.impl.RewardEvaluatorServiceImpl;
import com.scratch.game.service.impl.WinningEvaluatorServiceImpl;
import com.scratch.game.utils.JsonUtils;

public class ScratchGameMain {

    public static void main(String[] args) throws ScratchGameException {

        args = new String[]{"--config", "src/main/resources/config.json", "--betting-amount", "100"};

        ArgumentsService argumentsService = new ArgumentsServiceImpl();
        MatrixFillService matrixFillService = new MatrixFillServiceImpl();
        WinningEvaluatorService winningEvaluatorService = new WinningEvaluatorServiceImpl();
        RewardEvaluatorService rewardEvaluatorService = new RewardEvaluatorServiceImpl();

        GameConfig gameConfig = JsonUtils.fromJsonToObject(argumentsService.parseArguments(args).configFilePath, GameConfig.class);

        String[][] matrix = matrixFillService.fillMatrixWithStandardSymbols(gameConfig);

        WinningCombinations winningCombinations = winningEvaluatorService.evaluateWinningCombinations(gameConfig, matrix);

        double reward = rewardEvaluatorService.evaluateStandardReward(winningCombinations.getSymbolRewards(), gameConfig);

        String appliedBonusSymbol = matrixFillService.fillMatrixWithBonusSymbol(matrix, gameConfig, reward);

        double totalReward = rewardEvaluatorService.evaluateBonusReward(gameConfig.symbols, appliedBonusSymbol, reward);

        GameResult gameResult = new GameResult(matrix, totalReward, winningCombinations.getAppliedWinningCombinations(), appliedBonusSymbol);

        JsonUtils.printGameResult(gameResult);
    }
}
