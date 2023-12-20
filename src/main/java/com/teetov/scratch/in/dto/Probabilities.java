package com.teetov.scratch.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Probabilities {
    @JsonProperty("standard_symbols")
    private List<StandardSymbols> standardSymbols;
    @JsonProperty("bonus_symbols")
    private BonusSymbols bonusSymbols;

    public List<StandardSymbols> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(List<StandardSymbols> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public BonusSymbols getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(BonusSymbols bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }

    @Override
    public String toString() {
        return "Probabilities{" +
                "standardSymbols=" + standardSymbols +
                ", bonusSymbols=" + bonusSymbols +
                '}';
    }
}

