package com.scratch.game.service.impl;

import com.scratch.game.domain.GameConfig;
import com.scratch.game.service.MatrixFillService;
import com.scratch.game.utils.SymbolUtils;

import java.util.Map;
import java.util.Random;

public class MatrixFillServiceImpl implements MatrixFillService {

    @Override
    public String[][] fillMatrixWithStandardSymbols(GameConfig gameConfig) {

        String[][] matrix = new String[gameConfig.rows][gameConfig.columns];

        for (GameConfig.Probabilities.StandardSymbol standardSymbol : gameConfig.probabilities.standardSymbols) {
            int column = standardSymbol.column;
            int row = standardSymbol.row;

            matrix[row][column] = SymbolUtils.getRandomSymbol(standardSymbol.symbols);
        }

        return matrix;
    }

    @Override
    public String fillMatrixWithBonusSymbol(String[][] matrix, GameConfig gameConfig, double reward) {

        Map<String, Integer> bonusSymbols = gameConfig.probabilities.bonusSymbols.symbols;

        Random random = new Random();

        if (random.nextBoolean()) {
            int randomRow = random.nextInt(gameConfig.rows);
            int randomColumn = random.nextInt(gameConfig.columns);

            String bonusSymbol = SymbolUtils.getRandomSymbol(bonusSymbols);

            matrix[randomRow][randomColumn] = bonusSymbol;

            return bonusSymbol;
        }

        return null;
    }
}
