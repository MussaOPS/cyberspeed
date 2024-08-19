package com.scratch.game.service.impl;

import com.scratch.game.constants.Constants;
import com.scratch.game.constants.ExceptionConstants;
import com.scratch.game.domain.Arguments;
import com.scratch.game.errors.ScratchGameException;
import com.scratch.game.service.ArgumentsService;

public class ArgumentsServiceImpl implements ArgumentsService {

    @Override
    public Arguments parseArguments(String[] args) throws ScratchGameException {

        String configFilePath = null;
        Integer bettingAmount = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(Constants.CONFIG_FLAG)) {
                configFilePath = this.parseConfigFilePath(args, i + 1);
            } else if (args[i].equals(Constants.BETTING_AMOUNT_FLAG)) {
                bettingAmount = this.parseBettingAmount(args, i + 1);
            }
        }

        return new Arguments(configFilePath, bettingAmount);
    }

    private String parseConfigFilePath(String[] args, int index) throws ScratchGameException {

        if (index >= args.length || args[index] == null) {
            throw new ScratchGameException(ExceptionConstants.INVALID_CONFIG_ARGUMENT);
        }

        return args[index];
    }

    private int parseBettingAmount(String[] args, int index) throws ScratchGameException {

        if (index >= args.length || args[index] == null) {
            throw new ScratchGameException(ExceptionConstants.INVALID_BETTING_ARGUMENT);
        }

        try {
            return Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            throw new ScratchGameException(ExceptionConstants.INVALID_BETTING_FORMAT);
        }
    }
}
