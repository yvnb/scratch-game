package com.teetov.scratch.model.symbol;

import com.teetov.scratch.exception.ScratchGameException;

import java.util.HashMap;
import java.util.Map;

public class Symbols {

    private final Map<String, StandardSymbol> symbolMap = new HashMap<>();
    private final Map<String, BonusSymbol> bonusSymbolMap = new HashMap<>();

    public Symbols(Map<String, com.teetov.scratch.in.dto.Symbol> symbolsConfig) {
        symbolsConfig.forEach((name, sbl) -> {
            if (sbl.getType().equals("standard")) {
                symbolMap.put(name, new StandardSymbol(name, sbl.getRewardMultiplier()));
            } else if (sbl.getType().equals("bonus")) {
                bonusSymbolMap.put(name, new BonusSymbol(name, sbl.getImpact(), sbl.getRewardMultiplier(), sbl.getExtra()));
            } else {
                throw new ScratchGameException("Unknown symbol type: " + sbl.getType());
            }
        });
    }

    public Symbol get(String displayedName) {
        if (symbolMap.containsKey(displayedName)) {
            return symbolMap.get(displayedName);
        }
        if (bonusSymbolMap.containsKey(displayedName)) {
            return bonusSymbolMap.get(displayedName);
        }
        throw new ScratchGameException("Symbol not specified " + displayedName);
    }

    public BonusSymbol getBonusSymbol(String displayedName) {
        if (bonusSymbolMap.containsKey(displayedName)) {
            return bonusSymbolMap.get(displayedName);
        }
        throw new ScratchGameException("Symbol not specified " + displayedName);
    }
}
