package com.teetov.scratch;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.in.dto.GameConfig;
import com.teetov.scratch.in.model.GameParameters;
import com.teetov.scratch.in.service.ConfigReader;
import com.teetov.scratch.model.ScratchGame;
import com.teetov.scratch.out.service.ConsoleGameOutput;

public class Application {

    public static void main(String... args) {
        try {
            GameParameters gameParameters = new GameParameters(args);

            ObjectMapper mapper = new ObjectMapper();

            ConfigReader readerService = new ConfigReader(mapper);
            GameConfig gameConfig = readerService.readConfig(gameParameters.getConfigFilePath());

            ScratchGame game = new ScratchGame(gameParameters, gameConfig);
            ConsoleGameOutput consoleGameOutput = new ConsoleGameOutput(mapper);
            consoleGameOutput.print(game.getGameResult());
        } catch (ScratchGameException e) {
            System.err.println(e.getMessage());
        }
    }

}
