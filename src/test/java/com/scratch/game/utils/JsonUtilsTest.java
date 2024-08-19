package com.scratch.game.utils;

import com.scratch.game.domain.GameConfig;
import com.scratch.game.errors.ScratchGameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonUtilsTest {

    @Test
    void testFromJsonToObject_withValidJson_returnsObject() throws ScratchGameException {

        String filePath = "src/test/resources/config.json";

        GameConfig gameConfig = JsonUtils.fromJsonToObject(filePath, GameConfig.class);

        assertNotNull(gameConfig, "Result should not be null when valid JSON is provided.");
    }

    @Test
    void testFromJsonToObject_withInvalidJson_throwsException() {

        String filePath = "src/test/resources/invalid_game_result.json";

        assertThrows(ScratchGameException.class, () -> JsonUtils.fromJsonToObject(filePath, GameConfig.class));
    }

    @Test
    void testFromJsonToObject_withNonExistentFile_throwsException() {

        String filePath = "src/test/resources/non_existent_file.json";

        assertThrows(ScratchGameException.class, () -> JsonUtils.fromJsonToObject(filePath, GameConfig.class));
    }

    @Test
    void testFromJsonToObject_withEmptyJson_throwsException() {

        String filePath = "src/test/resources/empty_game_result.json";

        assertThrows(ScratchGameException.class, () -> JsonUtils.fromJsonToObject(filePath, GameConfig.class));
    }

    @Test
    void testFromJsonToObject_withCorruptedJson_throwsException() {

        String filePath = "src/test/resources/corrupted_game_result.json";

        assertThrows(ScratchGameException.class, () -> JsonUtils.fromJsonToObject(filePath, GameConfig.class));
    }
}
