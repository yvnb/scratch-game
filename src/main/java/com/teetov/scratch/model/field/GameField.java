package com.teetov.scratch.model.field;

import com.teetov.scratch.in.dto.Probabilities;
import com.teetov.scratch.in.dto.StandardSymbols;
import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.model.symbol.BonusSymbol;
import com.teetov.scratch.model.symbol.Symbol;
import com.teetov.scratch.model.symbol.Symbols;

import java.util.*;

public class GameField {

    private final Random random = new java.util.Random();
    private final List<List<Symbol>> matrix;
    private final BonusSymbol bonusSymbols;

    public GameField(int columns, int rows, Probabilities probabilities, Symbols symbols) {
        check(columns, rows, probabilities);
        matrix = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            ArrayList<Symbol> rowList = new ArrayList<>(columns);
            matrix.add(rowList);
            for (int column = 0; column < columns; column++) {
                rowList.add(null);
            }
        }
        bonusSymbols = addBonusSymbol(columns, rows, probabilities, symbols);
        fillWithStandardSymbols(probabilities, matrix, symbols);
        for (int i = 0; i < rows; i++) {
            for (int column = 0; column < columns; column++) {
                if (matrix.get(i).get(column) == null) {
                    throw new ScratchGameException("Cell with coordinates " + i + ":" + column + " is not filed with symbol");
                }
            }
        }
    }

    private void check(int columns, int rows, Probabilities probabilities) {
        for (StandardSymbols standardSymbols : probabilities.getStandardSymbols()) {
            if (standardSymbols.getRow() >= rows || standardSymbols.getColumn() >= columns) {
                throw new ScratchGameException(String.format("Address %s %s from probabilities config not exist",
                        standardSymbols.getRow(), standardSymbols.getColumn()));
            }
        }
    }

    private BonusSymbol addBonusSymbol(int columns, int rows, Probabilities probabilities, Symbols symbols) {
        String bonusSymbolName = generateRandomSymbol(probabilities.getBonusSymbols().getSymbols());
        int row = random.nextInt(rows);
        int column = random.nextInt(columns);
        BonusSymbol bonusSymbol = symbols.getBonusSymbol(bonusSymbolName);
        matrix.get(row).set(column, bonusSymbol);
        return bonusSymbol;
    }

    private void fillWithStandardSymbols(Probabilities probabilities, List<List<Symbol>> matrix, Symbols symbols) {
        for (StandardSymbols standardSymbols : probabilities.getStandardSymbols()) {
            if (matrix.get(standardSymbols.getRow()).get(standardSymbols.getColumn()) != null) {
                continue;
            }
            Map<String, Integer> symbolsProbabilities = standardSymbols.getSymbols();
            String symbolName = generateRandomSymbol(symbolsProbabilities);

            matrix.get(standardSymbols.getRow())
                    .set(standardSymbols.getColumn(), symbols.get(symbolName));
        }
    }

    private String generateRandomSymbol(Map<String, Integer> symbolsProbabilities) {
        int total = symbolsProbabilities.values().stream().mapToInt(i -> i).sum();
        int idx = random.nextInt(total);
        int probabilitiesSum = 0;
        String selectedSymbol = null;
        for (Map.Entry<String, Integer> probabilitiesEntry : symbolsProbabilities.entrySet()) {
            probabilitiesSum += probabilitiesEntry.getValue();
            if (idx < probabilitiesSum) {
                selectedSymbol = probabilitiesEntry.getKey();
                break;
            }
        }
        return selectedSymbol;
    }

    public List<List<Symbol>> getMatrix() {
        return matrix;
    }

    public BonusSymbol getBonus() {
        return bonusSymbols;
    }
}
