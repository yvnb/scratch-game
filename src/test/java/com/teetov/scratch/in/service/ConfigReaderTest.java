package com.teetov.scratch.in.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.teetov.scratch.in.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ConfigReaderTest {

    private ConfigReader service;

    @BeforeEach
    public void beforeEach() {
        service = new ConfigReader(new ObjectMapper());
    }

    @Test
    void readValidConfigFile() {
        GameConfig config = service.readConfig("src/test/resources/configOk.json");

        assertEquals(3, config.getColumns());
        assertEquals(3, config.getRows());
        assertEquals(11, config.getSymbols().size());
        Symbol standardSymbolA = config.getSymbols().get("A");
        assertEquals(BigDecimal.valueOf(50), standardSymbolA.getRewardMultiplier());
        assertEquals("standard", standardSymbolA.getType());
        Symbol bonusSymbol10x = config.getSymbols().get("10x");
        assertEquals(BigDecimal.valueOf(10), bonusSymbol10x.getRewardMultiplier());
        assertEquals("bonus", bonusSymbol10x.getType());
        assertEquals("multiply_reward", bonusSymbol10x.getImpact());
        assertEquals(9, config.getProbabilities().getStandardSymbols().size());
        StandardSymbols standardSymbols = config.getProbabilities().getStandardSymbols().get(0);
        assertEquals(0, standardSymbols.getColumn());
        assertEquals(0, standardSymbols.getRow());
        assertEquals(6, standardSymbols.getSymbols().size());
        assertEquals(1, standardSymbols.getSymbols().get("A"));
        BonusSymbols bonusSymbols = config.getProbabilities().getBonusSymbols();
        assertEquals(5, bonusSymbols.getSymbols().size());
        assertEquals(1, bonusSymbols.getSymbols().get("10x"));
        assertEquals(11, config.getWinCombinations().size());
        WinCombination combinationSame3Times = config.getWinCombinations().get("same_symbol_3_times");
        assertEquals(BigDecimal.valueOf(1), combinationSame3Times.getRewardMultiplier());
        assertEquals("same_symbols", combinationSame3Times.getWhen());
        assertEquals(3, combinationSame3Times.getCount());
        assertEquals("same_symbols", combinationSame3Times.getGroup());
        WinCombination combinationSameHorizontally = config.getWinCombinations().get("same_symbols_horizontally");
        assertEquals(BigDecimal.valueOf(2), combinationSameHorizontally.getRewardMultiplier());
        assertEquals("linear_symbols", combinationSameHorizontally.getWhen());
        assertEquals("horizontally_linear_symbols", combinationSameHorizontally.getGroup());
        assertEquals(3, combinationSameHorizontally.getCoveredAreas().size());
        assertEquals(3, combinationSameHorizontally.getCoveredAreas().get(0).size());
    }
}
