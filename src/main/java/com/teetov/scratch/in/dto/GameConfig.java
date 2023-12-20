package com.teetov.scratch.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GameConfig {
    private Integer columns;
    private Integer rows;
    private Map<String, Symbol> symbols;
    private Probabilities probabilities;

    @JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinations;

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Symbol> symbols) {
        this.symbols = symbols;
    }

    public Probabilities getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(Probabilities probabilities) {
        this.probabilities = probabilities;
    }

    public Map<String, WinCombination> getWinCombinations() {
        return winCombinations;
    }

    public void setWinCombinations(Map<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    @Override
    public String toString() {
        return "GameConfig{" +
                "columns=" + columns +
                ", rows=" + rows +
                ", symbols=" + symbols +
                ", probabilities=" + probabilities +
                ", winCombinations=" + winCombinations +
                '}';
    }
}
