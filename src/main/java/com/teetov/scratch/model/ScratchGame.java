package com.teetov.scratch.model;

import com.teetov.scratch.in.dto.GameConfig;
import com.teetov.scratch.in.model.GameParameters;
import com.teetov.scratch.model.field.GameField;
import com.teetov.scratch.model.reward.Reward;
import com.teetov.scratch.model.symbol.StandardSymbol;
import com.teetov.scratch.model.symbol.Symbols;
import com.teetov.scratch.model.win.WinRules;
import com.teetov.scratch.model.win.WonCombination;
import com.teetov.scratch.out.dto.GameResult;

import java.util.List;
import java.util.Map;

public class ScratchGame {

    private final GameResult gameResult;

    public ScratchGame(GameParameters parameters, GameConfig config) {
        Symbols symbols = new Symbols(config.getSymbols());
        GameField gameField = new GameField(config.getColumns(), config.getRows(), config.getProbabilities(), symbols);
        WinRules winRules = new WinRules(config.getWinCombinations());
        Map<StandardSymbol, List<WonCombination>> wonCombinations = winRules.calculateOutcome(gameField);
        Reward reward = new Reward(parameters.getBet(), wonCombinations, gameField.getBonus());

        this.gameResult = new GameResult(gameField.getMatrix(), reward.getAmount(), wonCombinations, gameField.getBonus());
    }

    public GameResult getGameResult() {
        return gameResult;
    }
}
