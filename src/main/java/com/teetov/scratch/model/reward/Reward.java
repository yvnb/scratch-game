package com.teetov.scratch.model.reward;

import com.teetov.scratch.model.symbol.BonusSymbol;
import com.teetov.scratch.model.symbol.StandardSymbol;
import com.teetov.scratch.model.win.WonCombination;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Reward {

    private final BigDecimal amount;

    public Reward(
            BigDecimal betAmount,
            Map<StandardSymbol, List<WonCombination>> wonCombinations,
            BonusSymbol bonus
    ) {
        BigDecimal rewardSum = BigDecimal.ZERO;
        for (Map.Entry<StandardSymbol, List<WonCombination>> entry : wonCombinations.entrySet()) {
            StandardSymbol symbol = entry.getKey();
            List<WonCombination> combinations = entry.getValue();
            BigDecimal symbolReward = betAmount.multiply(symbol.getRewardMultiplier()).multiply(
                    combinations.stream()
                            .map(WonCombination::getRewardMultiplier)
                            .reduce(BigDecimal.ONE, BigDecimal::multiply)
            );
            rewardSum = rewardSum.add(symbolReward);
        }
        this.amount = bonus.applyBonus(rewardSum);
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
