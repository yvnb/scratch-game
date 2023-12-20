package com.teetov.scratch.in.dto;

import java.util.Map;

public class BonusSymbols {
    private Map<String, Integer> symbols;

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        return "BonusSymbols{" +
                "symbols=" + symbols +
                '}';
    }
}