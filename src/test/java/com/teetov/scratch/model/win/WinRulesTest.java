package com.teetov.scratch.model.win;

import com.teetov.scratch.in.dto.WinCombination;
import com.teetov.scratch.model.symbol.BonusSymbol;
import com.teetov.scratch.model.field.GameField;
import com.teetov.scratch.model.symbol.StandardSymbol;
import com.teetov.scratch.model.symbol.Symbol;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WinRulesTest {

    @Test
    void calculateGameOutcome_win1() {
        WinRules winRules = generateWinCombinations();
        Symbol a = new StandardSymbol("A", BigDecimal.valueOf(50));
        Symbol b = new StandardSymbol("B", BigDecimal.valueOf(25));
        Symbol plus1000 = new BonusSymbol("+1000", "extra_bonus", null, BigDecimal.valueOf(1000));

        GameField field = Mockito.mock(GameField.class);
        Mockito.doReturn(Arrays.asList(
                Arrays.asList(a, a, b),
                Arrays.asList(a, plus1000, b),
                Arrays.asList(a, a, b)
        )).when(field).getMatrix();
        Mockito.doReturn(plus1000).when(field).getBonus();

        Map<StandardSymbol, List<WonCombination>> outcome = winRules.calculateOutcome(field);
        assertEquals(2, outcome.size());
        assertEquals(2, outcome.get(a).size());
        assertTrue(outcome.get(a).contains(new WonCombination("same_symbol_5_times", BigDecimal.valueOf(2))));
        assertTrue(outcome.get(a).contains(new WonCombination("same_symbols_vertically", BigDecimal.valueOf(2))));
        assertEquals(2, outcome.get(b).size());
        assertTrue(outcome.get(b).contains(new WonCombination("same_symbol_3_times", BigDecimal.valueOf(1))));
        assertTrue(outcome.get(b).contains(new WonCombination("same_symbols_vertically", BigDecimal.valueOf(2))));
    }

    @Test
    void calculateGameOutcome_win2() {
        WinRules winRules = generateWinCombinations();
        Symbol a = new StandardSymbol("A", BigDecimal.valueOf(50));
        Symbol b = new StandardSymbol("B", BigDecimal.valueOf(25));
        Symbol c = new StandardSymbol("C", BigDecimal.valueOf(10));
        Symbol d = new StandardSymbol("D", BigDecimal.valueOf(5));
        Symbol e = new StandardSymbol("E", BigDecimal.valueOf(3));
        Symbol f = new StandardSymbol("F", BigDecimal.valueOf(1.5));
        Symbol x10 = new BonusSymbol("10x", "multiply_reward", BigDecimal.valueOf(10), null);

        GameField field = Mockito.mock(GameField.class);
        Mockito.doReturn(Arrays.asList(
                Arrays.asList(a, b, c),
                Arrays.asList(e, b, x10),
                Arrays.asList(f, d, b)
        )).when(field).getMatrix();
        Mockito.doReturn(x10).when(field).getBonus();

        Map<StandardSymbol, List<WonCombination>> outcome = winRules.calculateOutcome(field);
        assertEquals(1, outcome.size());
        assertEquals(1, outcome.get(b).size());
        assertTrue(outcome.get(b).contains(new WonCombination("same_symbol_3_times", BigDecimal.valueOf(1))));
    }

    @Test
    void calculateGameOutcome_lost1() {
        WinRules winRules = generateWinCombinations();
        Symbol a = new StandardSymbol("A", BigDecimal.valueOf(50));
        Symbol b = new StandardSymbol("B", BigDecimal.valueOf(25));
        Symbol c = new StandardSymbol("C", BigDecimal.valueOf(10));
        Symbol d = new StandardSymbol("D", BigDecimal.valueOf(5));
        Symbol e = new StandardSymbol("E", BigDecimal.valueOf(3));
        Symbol f = new StandardSymbol("F", BigDecimal.valueOf(1.5));
        Symbol x5 = new BonusSymbol("5x", "multiply_reward", BigDecimal.valueOf(5), null);

        GameField field = Mockito.mock(GameField.class);
        Mockito.doReturn(Arrays.asList(
                Arrays.asList(a, b, c),
                Arrays.asList(e, b, x5),
                Arrays.asList(f, d, c)
        )).when(field).getMatrix();
        Mockito.doReturn(x5).when(field).getBonus();

        Map<StandardSymbol, List<WonCombination>> outcome = winRules.calculateOutcome(field);
        assertEquals(0, outcome.size());
    }

    @Test
    void calculateGameOutcome_lost2() {
        WinRules winRules = generateWinCombinations();
        Symbol a = new StandardSymbol("A", BigDecimal.valueOf(50));
        Symbol b = new StandardSymbol("B", BigDecimal.valueOf(25));
        Symbol c = new StandardSymbol("C", BigDecimal.valueOf(10));
        Symbol d = new StandardSymbol("D", BigDecimal.valueOf(5));
        Symbol e = new StandardSymbol("E", BigDecimal.valueOf(3));
        Symbol f = new StandardSymbol("F", BigDecimal.valueOf(1.5));
        Symbol x5 = new BonusSymbol("5x", "multiply_reward", BigDecimal.valueOf(5), null);

        GameField field = Mockito.mock(GameField.class);
        Mockito.doReturn(Arrays.asList(
                Arrays.asList(f, d, c),
                Arrays.asList(b, b, f),
                Arrays.asList(a, c, x5)
        )).when(field).getMatrix();
        Mockito.doReturn(x5).when(field).getBonus();

        Map<StandardSymbol, List<WonCombination>> outcome = winRules.calculateOutcome(field);
        assertEquals(0, outcome.size());
    }

    private WinRules generateWinCombinations() {
        Map<String, WinCombination> winCombinations = new HashMap<>();
        winCombinations.put("same_symbol_3_times", new WinCombination(BigDecimal.ONE, "same_symbols", 3, "same_symbols", null));
        winCombinations.put("same_symbol_4_times", new WinCombination(BigDecimal.valueOf(1.5), "same_symbols", 4, "same_symbols", null));
        winCombinations.put("same_symbol_5_times", new WinCombination(BigDecimal.valueOf(2), "same_symbols", 5, "same_symbols", null));
        winCombinations.put("same_symbol_6_times", new WinCombination(BigDecimal.valueOf(3), "same_symbols", 6, "same_symbols", null));
        winCombinations.put("same_symbol_7_times", new WinCombination(BigDecimal.valueOf(5), "same_symbols", 7, "same_symbols", null));
        winCombinations.put("same_symbol_8_times", new WinCombination(BigDecimal.valueOf(10), "same_symbols", 8, "same_symbols", null));
        winCombinations.put("same_symbol_9_times", new WinCombination(BigDecimal.valueOf(20), "same_symbols", 9, "same_symbols", null));
        winCombinations.put("same_symbols_horizontally", new WinCombination(BigDecimal.valueOf(2), "linear_symbols", null, "horizontally_linear_symbols",
                Arrays.asList(Arrays.asList("0:0", "0:1", "0:2"), Arrays.asList("1:0", "1:1", "1:2"), Arrays.asList("2:0", "2:1", "2:2"))));
        winCombinations.put("same_symbols_vertically", new WinCombination(BigDecimal.valueOf(2), "linear_symbols", null, "vertically_linear_symbols",
                Arrays.asList(Arrays.asList("0:0", "1:0", "2:0"), Arrays.asList("0:1", "1:1", "2:1"), Arrays.asList("0:2", "1:2", "2:2"))));
        winCombinations.put("same_symbols_diagonally_left_to_right", new WinCombination(BigDecimal.valueOf(5), "linear_symbols", null, "ltr_diagonally_linear_symbols",
                Collections.singletonList(Arrays.asList("0:0", "1:1", "2:2"))));
        winCombinations.put("same_symbols_diagonally_right_to_left", new WinCombination(BigDecimal.valueOf(5), "linear_symbols", null, "rtl_diagonally_linear_symbols",
                Collections.singletonList(Arrays.asList("0:2", "1:1", "2:0"))));
        return new WinRules(winCombinations);
    }

}
