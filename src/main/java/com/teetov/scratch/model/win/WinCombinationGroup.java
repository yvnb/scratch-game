package com.teetov.scratch.model.win;

import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.model.field.GameField;
import com.teetov.scratch.model.symbol.StandardSymbol;

import java.util.Map;

public abstract class WinCombinationGroup {

    protected final String groupName;

    protected WinCombinationGroup(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            throw new ScratchGameException("Win combination group name is empty");
        }
        this.groupName = groupName;
    }

    public abstract Map<StandardSymbol, WonCombination> getWonCombinations(GameField gameField);
}
