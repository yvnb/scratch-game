package com.teetov.scratch.in.model;

import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.in.model.GameParameters;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GameParametersTest {

    @Test
    void correctFullCommandLineArguments() {
        String[] args = { "--config", "config.json", "--betting-amount", "100"};
        GameParameters commandArgs = new GameParameters(args);
        assertEquals("config.json", commandArgs.getConfigFilePath());
        assertEquals(BigDecimal.valueOf(100), commandArgs.getBet().setScale(0));

        String[] argsAnotherOrder = { "--betting-amount", "100", "--config", "config.json" };
        GameParameters commandArgs2 = new GameParameters(argsAnotherOrder);
        assertEquals("config.json", commandArgs2.getConfigFilePath());
        assertEquals(BigDecimal.valueOf(100), commandArgs2.getBet().setScale(0));

        String[] argsAdditionalUnusedArg = { "--betting-amount", "100", "--unused", "--config", "config.json" };
        GameParameters commandArgs3 = new GameParameters(argsAdditionalUnusedArg);
        assertEquals("config.json", commandArgs3.getConfigFilePath());
        assertEquals(BigDecimal.valueOf(100), commandArgs3.getBet().setScale(0));
    }

    @Test
    void oneRequiredArgMissed() {
        String[] args = {"--config", "config.json"};
        ScratchGameException exception = assertThrows(ScratchGameException.class, () -> new GameParameters(args));
        assertEquals("Some of required properties are missed: --betting-amount", exception.getMessage());
    }

    @Test
    void noArgument() {
        String[] args = {};
        ScratchGameException exception = assertThrows(ScratchGameException.class, () -> new GameParameters(args));
        assertTrue(exception.getMessage().startsWith("Some of required properties are missed: --config, --betting-amount"));
    }

    @Test
    void wrongBettingFormat() {
        String[] args = {"--config", "config.json", "--betting-amount", "100abc"};
        ScratchGameException exception = assertThrows(ScratchGameException.class, () -> new GameParameters(args));
        assertEquals("--betting-amount argument has not valid value 100abc", exception.getMessage());
    }

    @Test
    void doubledArgument() {
        String[] args = {"--config", "config.json", "--config", "config2.json"};
        ScratchGameException exception = assertThrows(ScratchGameException.class, () -> new GameParameters(args));
        assertEquals("--config has conflict with same already passed argument", exception.getMessage());
    }
}
