package com.teetov.scratch.model.win;

import com.teetov.scratch.exception.ScratchGameException;

import java.math.BigDecimal;
import java.util.Objects;

public class WonCombination {

    private final String name;
    private final BigDecimal rewardMultiplier;

    public WonCombination(String name, BigDecimal rewardMultiplier) {
        if (name == null || name.isEmpty()) {
            throw new ScratchGameException("Win combination name is empty");
        }
        if (rewardMultiplier == null) {
            throw new ScratchGameException("Reward multiplier for win combination " + name + " is empty");
        }
        this.name = name;
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRewardMultiplier() {
        return rewardMultiplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WonCombination that = (WonCombination) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(rewardMultiplier, that.rewardMultiplier);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (rewardMultiplier != null ? rewardMultiplier.hashCode() : 0);
        return result;
    }
}
