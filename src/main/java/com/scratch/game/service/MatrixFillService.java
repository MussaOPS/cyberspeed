package com.scratch.game.service;

import com.scratch.game.domain.GameConfig;

public interface MatrixFillService {

    String[][] fillMatrixWithStandardSymbols(GameConfig gameConfig);

    String fillMatrixWithBonusSymbol(String[][] matrix, GameConfig gameConfig, double reward);
}
