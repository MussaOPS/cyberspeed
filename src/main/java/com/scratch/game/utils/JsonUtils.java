package com.scratch.game.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratch.game.constants.ExceptionConstants;
import com.scratch.game.domain.GameResult;
import com.scratch.game.errors.ScratchGameException;

import java.io.File;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJsonToObject(String filePath, Class<T> clazz) throws ScratchGameException {

        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (Exception e) {
            throw new ScratchGameException(ExceptionConstants.JSON_PARSING_ERROR);
        }
    }

    public static void printGameResult(GameResult result) throws ScratchGameException {

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, result);
        } catch (Exception e) {
            throw new ScratchGameException(ExceptionConstants.OBJECT_PARSING_ERROR);
        }
    }
}
