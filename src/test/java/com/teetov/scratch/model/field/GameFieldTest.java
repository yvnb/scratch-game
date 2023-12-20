package com.teetov.scratch.model.field;

import com.teetov.scratch.in.dto.BonusSymbols;
import com.teetov.scratch.in.dto.Probabilities;
import com.teetov.scratch.in.dto.StandardSymbols;
import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.model.symbol.Symbol;
import com.teetov.scratch.model.symbol.Symbols;
import com.teetov.scratch.model.symbol.SymbolsTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {

    @Test
    void generateGameField() {
        int columns = 3;
        int rows = 3;

        Probabilities probabilities = generatePossibilities(columns, rows);
        Symbols symbols = new Symbols(SymbolsTest.generateSymbols());

        GameField gameField = new GameField(columns, rows, probabilities, symbols);

        List<List<Symbol>> matrix = gameField.getMatrix();

        assertEquals(rows, matrix.size());
        for (int i = 0; i < rows; i++) {
            assertEquals(columns, matrix.get(i).size());
        }

        assertNotNull(gameField.getBonus());
        assertTrue(probabilities.getBonusSymbols().getSymbols().containsKey(gameField.getBonus().getName()));
    }

    @Test
    void notConsistentConfig() {
        int columns = 3;
        int rows = 3;

        Probabilities probabilities = generatePossibilities(columns, rows);
        Symbols symbols = new Symbols(SymbolsTest.generateSymbols());

        assertThrows(ScratchGameException.class, () -> new GameField(1, 2, probabilities, symbols));
    }

    public static Probabilities generatePossibilities(int columns, int rows) {
        Probabilities probabilities = new Probabilities();
        ArrayList<StandardSymbols> standardSymbols = generateStandardSymbols(columns, rows);
        probabilities.setStandardSymbols(standardSymbols);
        BonusSymbols bonusSymbols = generateBonusSymbols();
        probabilities.setBonusSymbols(bonusSymbols);
        return probabilities;
    }

    static ArrayList<StandardSymbols> generateStandardSymbols(int columns, int rows) {
        ArrayList<StandardSymbols> standardSymbols = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                StandardSymbols standard = new StandardSymbols();
                standard.setRow(row);
                standard.setColumn(column);
                HashMap<String, Integer> symbols = new HashMap<>();
                symbols.put("A", 1);
                symbols.put("B", 2);
                symbols.put("C", 3);
                symbols.put("D", 4);
                symbols.put("E", 5);
                symbols.put("F", 6);
                standard.setSymbols(symbols);
                standardSymbols.add(standard);
            }
        }
        return standardSymbols;
    }

    static BonusSymbols generateBonusSymbols() {
        BonusSymbols bonusSymbols = new BonusSymbols();
        HashMap<String, Integer> bonusSymbolsMap = new HashMap<>();
        bonusSymbolsMap.put("10x", 1);
        bonusSymbolsMap.put("5x", 2);
        bonusSymbolsMap.put("+1000", 3);
        bonusSymbolsMap.put("+500", 4);
        bonusSymbolsMap.put("MISS", 5);
        bonusSymbols.setSymbols(bonusSymbolsMap);
        return bonusSymbols;
    }
}
