package com.teetov.scratch.in.dto;

import java.util.Map;

public class StandardSymbols {
    private Integer column;
    private Integer row;
    private Map<String, Integer> symbols;

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        return "StandardSymbols{" +
                "column=" + column +
                ", row=" + row +
                ", symbols=" + symbols +
                '}';
    }
}