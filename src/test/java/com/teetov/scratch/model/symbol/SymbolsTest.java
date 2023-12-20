package com.teetov.scratch.model.symbol;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SymbolsTest {

    @Test
    void createValidSymbols() {
        Symbols symbols = new Symbols(generateSymbols());

        Symbol symbolA = symbols.get("A");
        assertTrue(symbolA instanceof StandardSymbol);
        StandardSymbol standardSymbolA = (StandardSymbol) symbolA;
        assertEquals(BigDecimal.valueOf(50), standardSymbolA.getRewardMultiplier());

        Symbol symbol10x = symbols.get("10x");
        assertTrue(symbol10x instanceof BonusSymbol);
        BonusSymbol bonusSymbol10x = (BonusSymbol) symbol10x;
        assertEquals(BigDecimal.valueOf(100), bonusSymbol10x.applyBonus(BigDecimal.TEN));

        Symbol symbolPlus500 = symbols.get("+500");
        assertTrue(symbolPlus500 instanceof BonusSymbol);
        BonusSymbol bonusSymbolPlus500 = (BonusSymbol) symbolPlus500;
        assertEquals(BigDecimal.valueOf(510), bonusSymbolPlus500.applyBonus(BigDecimal.TEN));

        Symbol symbolMiss = symbols.get("MISS");
        assertTrue(symbolMiss instanceof BonusSymbol);
        BonusSymbol bonusSymbolMiss = (BonusSymbol) symbolMiss;
        assertEquals(BigDecimal.TEN, bonusSymbolMiss.applyBonus(BigDecimal.TEN));
    }

    public static HashMap<String, com.teetov.scratch.in.dto.Symbol> generateSymbols() {
        HashMap<String, com.teetov.scratch.in.dto.Symbol> symbolsConfig = new HashMap<>();
        symbolsConfig.put("A", new com.teetov.scratch.in.dto.Symbol(BigDecimal.valueOf(50), "standard", null, null));
        symbolsConfig.put("B", new com.teetov.scratch.in.dto.Symbol(BigDecimal.valueOf(25), "standard", null, null));
        symbolsConfig.put("C", new com.teetov.scratch.in.dto.Symbol(BigDecimal.valueOf(10), "standard", null, null));
        symbolsConfig.put("D", new com.teetov.scratch.in.dto.Symbol(BigDecimal.valueOf(5), "standard", null, null));
        symbolsConfig.put("E", new com.teetov.scratch.in.dto.Symbol(BigDecimal.valueOf(3), "standard", null, null));
        symbolsConfig.put("F", new com.teetov.scratch.in.dto.Symbol(BigDecimal.valueOf(1.5), "standard", null, null));
        symbolsConfig.put("10x", new com.teetov.scratch.in.dto.Symbol(BigDecimal.valueOf(10), "bonus", "multiply_reward", null));
        symbolsConfig.put("5x", new com.teetov.scratch.in.dto.Symbol(BigDecimal.valueOf(5), "bonus", "multiply_reward", null));
        symbolsConfig.put("+1000", new com.teetov.scratch.in.dto.Symbol(null, "bonus", "extra_bonus", BigDecimal.valueOf(1000)));
        symbolsConfig.put("+500", new com.teetov.scratch.in.dto.Symbol(null, "bonus", "extra_bonus", BigDecimal.valueOf(500)));
        symbolsConfig.put("MISS", new com.teetov.scratch.in.dto.Symbol(null, "bonus", "miss", null));
        return symbolsConfig;
    }
}
