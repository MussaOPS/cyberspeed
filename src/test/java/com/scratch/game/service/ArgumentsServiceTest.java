package com.scratch.game.service;

import com.scratch.game.constants.ExceptionConstants;
import com.scratch.game.domain.Arguments;
import com.scratch.game.errors.ScratchGameException;
import com.scratch.game.service.impl.ArgumentsServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArgumentsServiceTest {

    ArgumentsServiceImpl argumentsService = new ArgumentsServiceImpl();

    @Test
    void testParseArguments_withValidArguments_returnsCorrectValues() throws ScratchGameException {

        String[] args = {"--config", "config.json", "--betting-amount", "100"};
        Arguments arguments = argumentsService.parseArguments(args);

        assertEquals("config.json", arguments.configFilePath);
        assertEquals(100, arguments.bettingAmount);
    }

    @Test
    void testParseArguments_withInvalidConfig_throwsException() {

        String[] args = {"--config"};

        ScratchGameException exception = assertThrows(ScratchGameException.class, () -> {
            argumentsService.parseArguments(args);
        });

        assertEquals(ExceptionConstants.INVALID_CONFIG_ARGUMENT, exception.getMessage());
    }

    @Test
    void testParseArguments_withInvalidBettingAmount_throwsException() {

        String[] args = {"--betting-amount", "abc"};

        ScratchGameException exception = assertThrows(ScratchGameException.class, () -> {
            argumentsService.parseArguments(args);
        });

        assertEquals(ExceptionConstants.INVALID_BETTING_FORMAT, exception.getMessage());
    }
}
