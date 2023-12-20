package com.teetov.scratch.model.symbol;

import com.teetov.scratch.exception.ScratchGameException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

public class BonusSymbol extends Symbol {

    private final Function<BigDecimal, BigDecimal> bonusAction;

    public BonusSymbol(String name, String impact, BigDecimal rewardMultiplier, BigDecimal extra) {
        super(name);
        if (impact == null) {
            throw new ScratchGameException("Impact for bonus symbol " + name + " is empty");
        }
        if (impact.equals("multiply_reward")) {
            if (rewardMultiplier == null) {
                throw new ScratchGameException("Reward multiplier for bonus symbol " + name + " is empty");
            }
            bonusAction = result -> result.multiply(rewardMultiplier);
        } else if (impact.equals("extra_bonus")) {
            if (extra == null) {
                throw new ScratchGameException("Extra bonus for bonus symbol " + name + " is empty");
            }
            bonusAction = result -> {
                if (result.equals(BigDecimal.ZERO)) {
                    return result;
                }
                return result.add(extra);
            };
        } else if (impact.equals("miss")) {
            bonusAction = result -> result;
        } else {
            throw new ScratchGameException("Unknown impact " + impact + " for bonus symbol " + name + " is empty");
        }
    }

    public BigDecimal applyBonus(BigDecimal wonAmount) {
        return bonusAction.apply(wonAmount);
    }
}
