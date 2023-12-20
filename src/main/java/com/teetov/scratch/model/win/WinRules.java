package com.teetov.scratch.model.win;

import com.teetov.scratch.in.dto.WinCombination;
import com.teetov.scratch.model.field.GameField;
import com.teetov.scratch.model.symbol.StandardSymbol;

import java.util.*;

public class WinRules {

    private final Set<WinCombinationGroup> winCombinationGroups;

    public WinRules(Map<String, WinCombination> winCombinations) {
        Map<String, WinCombination> withWhenSameSymbol = new HashMap<>();
        Map<String, WinCombination> withWhenLinearSymbols = new HashMap<>();
        winCombinations.forEach((name, combination) -> {
            if (combination.getWhen().equals("same_symbols")) {
                withWhenSameSymbol.put(name, combination);
            } else if (combination.getWhen().equals("linear_symbols")) {
                withWhenLinearSymbols.put(name, combination);
            }
        });

        winCombinationGroups = new HashSet<>();
        groupByGroupName(withWhenSameSymbol)
                .forEach((group, combinations) ->
                        winCombinationGroups.add(new SameSymbolWinGroup(group, combinations)));
        groupByGroupName(withWhenLinearSymbols)
                .forEach((group, combinations) ->
                        winCombinationGroups.add(new LinerWinGroup(group, combinations)));
    }

    private Map<String, Map<String, WinCombination>> groupByGroupName(
            Map<String, WinCombination> combinations
    ) {
        Map<String, Map<String, WinCombination>> result = new HashMap<>();
        combinations.forEach((name, combination) -> {
            result.putIfAbsent(combination.getGroup(), new HashMap<>());
            result.get(combination.getGroup()).put(name, combination);
        });
        return result;
    }

    public Map<StandardSymbol, List<WonCombination>> calculateOutcome(GameField field) {
        Map<StandardSymbol, List<WonCombination>> outcome = new HashMap<>();
        winCombinationGroups.forEach(group -> {
            Map<StandardSymbol, WonCombination> wonCombinations = group.getWonCombinations(field);
            wonCombinations.forEach((symbol, combination) -> {
                List<WonCombination> combinations = outcome.computeIfAbsent(symbol, k -> new ArrayList<>());
                combinations.add(combination);
            });
        });
        return outcome;
    }
}
