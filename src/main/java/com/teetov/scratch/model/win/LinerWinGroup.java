package com.teetov.scratch.model.win;

import com.teetov.scratch.in.dto.WinCombination;
import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.model.field.GameField;
import com.teetov.scratch.model.symbol.StandardSymbol;
import com.teetov.scratch.model.symbol.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinerWinGroup extends WinCombinationGroup {

    private final WonCombination wonCombination;
    private final List<List<Integer[]>> coveredAreas = new ArrayList<>();

    protected LinerWinGroup(
            String groupName,
            Map<String, WinCombination> winCombinationGroup
    ) {
        super(groupName);
        if (winCombinationGroup.size() > 1) {
            throw new ScratchGameException("Linear symbols win condition should have only one item in every group");
        }
        Map.Entry<String, WinCombination> combinationEntry = new ArrayList<>(winCombinationGroup.entrySet()).get(0);
        WinCombination winCombination = combinationEntry.getValue();
        this.wonCombination = new WonCombination(combinationEntry.getKey(), winCombination.getRewardMultiplier());

        winCombination.getCoveredAreas().forEach(lineConfig -> {
            ArrayList<Integer[]> lineParsed = new ArrayList<>();
            coveredAreas.add(lineParsed);
            lineConfig.forEach(coordinatesConfig ->
                lineParsed.add(parseCoordinate(coordinatesConfig))
            );
        });
    }

    private Integer[] parseCoordinate(String coordinatesConfig) {
        if (!coordinatesConfig.matches("\\d+:\\d+")) {
            throw new ScratchGameException("Unexpected covered area format " + coordinatesConfig);
        }
        String[] splitCoordinates = coordinatesConfig.split(":");
        int row = Integer.parseInt(splitCoordinates[0]);
        int column = Integer.parseInt(splitCoordinates[1]);
        return new Integer[]{row, column};
    }

    @Override
    public Map<StandardSymbol, WonCombination> getWonCombinations(GameField gameField) {
        List<List<Symbol>> matrix = gameField.getMatrix();
        Map<StandardSymbol, WonCombination> result = new HashMap<>();
        coveredAreas.forEach(area ->
            addToResultIfAreaFilledWithSameSymbol(matrix, result, area)
        );
        return result;
    }

    private void addToResultIfAreaFilledWithSameSymbol(
            List<List<Symbol>> matrix,
            Map<StandardSymbol, WonCombination> result,
            List<Integer[]> area
    ) {
        Symbol first = null;
        for (int i = 0; i < area.size(); i++) {
            Integer[] coordinate = area.get(i);
            Symbol symbol = matrix.get(coordinate[0]).get(coordinate[1]);
            if (symbol instanceof StandardSymbol) {
                StandardSymbol standardSymbol = (StandardSymbol) symbol;
                if (result.containsKey(standardSymbol)) {
                    break;
                }
                if (first == null) {
                    first = standardSymbol;
                } else if (standardSymbol != first) {
                    break;
                }
                if (i == area.size() - 1) {
                    result.put(standardSymbol, wonCombination);
                }
            } else {
                break;
            }
        }
    }
}
