package com.teetov.scratch.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teetov.scratch.model.symbol.BonusSymbol;
import com.teetov.scratch.model.symbol.StandardSymbol;
import com.teetov.scratch.model.symbol.Symbol;
import com.teetov.scratch.model.win.WonCombination;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameResult {

    private List<List<String>> matrix;
    private BigDecimal reward;
    @JsonProperty("applied_winning_combinations")
    private Map<String, List<String>> appliedWinningCombinations;
    @JsonProperty("applied_bonus_symbol")
    private String appliedBonusSymbol;

    public GameResult() {
    }

    public GameResult(
            List<List<Symbol>> matrix,
            BigDecimal reward,
            Map<StandardSymbol, List<WonCombination>> wonCombinations,
            BonusSymbol bonus
    ) {
        this.matrix = matrix.stream()
                .map(line ->
                        line.stream()
                                .map(Symbol::getName)
                                .collect(Collectors.toList()))
                .collect(Collectors.toList());
        this.reward = reward;
        this.appliedWinningCombinations = wonCombinations.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(),
                        entry -> entry.getValue().stream()
                                .map(WonCombination::getName)
                                .collect(Collectors.toList())
                ));
        this.appliedBonusSymbol = bonus.getName();
    }

    public List<List<String>> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<String>> matrix) {
        this.matrix = matrix;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }

    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public void setAppliedWinningCombinations(Map<String, List<String>> appliedWinningCombinations) {
        this.appliedWinningCombinations = appliedWinningCombinations;
    }

    public String getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }

    public void setAppliedBonusSymbol(String appliedBonusSymbol) {
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "matrix=" + matrix +
                ", reward='" + reward + '\'' +
                ", appliedWinningCombinations=" + appliedWinningCombinations +
                ", appliedBonusSymbol='" + appliedBonusSymbol + '\'' +
                '}';
    }
}
