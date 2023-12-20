package com.teetov.scratch.model.reward;

import com.teetov.scratch.model.reward.Reward;
import com.teetov.scratch.model.symbol.BonusSymbol;
import com.teetov.scratch.model.symbol.StandardSymbol;
import com.teetov.scratch.model.win.WonCombination;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardTest {

    @Test
    void reward_win1() {
        HashMap<StandardSymbol, List<WonCombination>> wonCombinations = new HashMap<>();
        wonCombinations.put(
                new StandardSymbol("A", BigDecimal.valueOf(5)),
                Arrays.asList(
                        new WonCombination("same_symbol_5_times", BigDecimal.valueOf(5)),
                        new WonCombination("same_symbols_vertically", BigDecimal.valueOf(2))
                ));
        wonCombinations.put(
                new StandardSymbol("B", BigDecimal.valueOf(3)),
                Arrays.asList(
                        new WonCombination("same_symbol_3_times", BigDecimal.valueOf(1)),
                        new WonCombination("same_symbols_vertically", BigDecimal.valueOf(2))
                ));
        BonusSymbol bonus = new BonusSymbol("+1000", "extra_bonus", null, BigDecimal.valueOf(1000));
        Reward reward = new Reward(BigDecimal.valueOf(100), wonCombinations, bonus);
        assertEquals(BigDecimal.valueOf(6600), reward.getAmount());
    }

    @Test
    void reward_won2() {
        HashMap<StandardSymbol, List<WonCombination>> wonCombinations = new HashMap<>();
        BonusSymbol bonus = new BonusSymbol("10x", "multiply_reward", BigDecimal.valueOf(10), null);
        wonCombinations.put(
                new StandardSymbol("B", BigDecimal.valueOf(25)),
                Arrays.asList(
                        new WonCombination("same_symbol_5_times", BigDecimal.valueOf(2))
                ));
        Reward reward = new Reward(BigDecimal.valueOf(100), wonCombinations, bonus);
        assertEquals(BigDecimal.valueOf(50_000), reward.getAmount());
    }

    @Test
    void reward_lost1() {
        HashMap<StandardSymbol, List<WonCombination>> wonCombinations = new HashMap<>();
        BonusSymbol bonus = new BonusSymbol("5x", "multiply_reward", BigDecimal.valueOf(5), null);
        Reward reward = new Reward(BigDecimal.valueOf(100), wonCombinations, bonus);
        assertEquals(BigDecimal.ZERO, reward.getAmount());
    }
}
