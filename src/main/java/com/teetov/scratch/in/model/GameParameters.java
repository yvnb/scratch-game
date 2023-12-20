package com.teetov.scratch.in.model;

import com.teetov.scratch.exception.ScratchGameException;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GameParameters {

    public enum Property {
        CONFIG(Collections.singletonList("--config"), arg -> !arg.isEmpty(), true),
        BETTING_AMOUNT(Collections.singletonList("--betting-amount"), arg -> {
            try {
                Double.valueOf(arg);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }, true);

        static Map<String, Property> getAllArgsValuesMap() {
            Map<String, Property> argsValue = new HashMap<>();
            for (Property property : Property.values()) {
                for (String arg : property.args) {
                    argsValue.put(arg, property);
                }
            }
            return argsValue;
        }

        private final List<String> args;
        private final Predicate<String> isValid;
        private final boolean isRequired;

        Property(List<String> args, Predicate<String> isValid, boolean isRequired) {
            this.args = args;
            this.isValid = isValid;
            this.isRequired = isRequired;
        }

        public boolean isRequired() {
            return isRequired;
        }
    }

    private final Map<Property, String> properties;

    public GameParameters(String[] args) {
        properties = parseCommandArgs(args);
    }

    private Map<Property, String> parseCommandArgs(String[] args) {
        Map<String, Property> valuesMap = Property.getAllArgsValuesMap();
        Map<Property, String> result = new EnumMap<>(Property.class);

        String parameterName = null;
        for (String arg : args) {
            if (parameterName == null) {
                if (valuesMap.containsKey(arg)) {
                    parameterName = arg;
                }
            } else {
                Property property = valuesMap.get(parameterName);
                if (result.containsKey(property)) {
                    throw new ScratchGameException(parameterName + " has conflict with same already passed argument");
                }
                if (!property.isValid.test(arg)) {
                    throw new ScratchGameException(parameterName + " argument has not valid value " + arg);
                }
                result.put(property, arg);
                parameterName = null;
            }
        }

        checkIfRequiredPropertiesAreMissed(result);
        return result;
    }

    private void checkIfRequiredPropertiesAreMissed(Map<Property, String> result) {
        List<Property> absentRequiredProperty = Arrays.stream(Property.values())
                .filter(Property::isRequired)
                .filter(prop -> !result.containsKey(prop))
                .collect(Collectors.toList());
        if (!absentRequiredProperty.isEmpty()) {
            StringBuilder message = buildMissedPropertiesMessage(absentRequiredProperty);
            throw new ScratchGameException(message.toString());
        }
    }

    private StringBuilder buildMissedPropertiesMessage(List<Property> absentRequiredProperty) {
        StringBuilder message = new StringBuilder("Some of required properties are missed: ");
        for (int i = 0; i < absentRequiredProperty.size() - 1; i++) {
            message.append(absentRequiredProperty.get(i).args.get(0))
                    .append(", ");
        }
        message.append(absentRequiredProperty.get(absentRequiredProperty.size() - 1).args.get(0));
        return message;
    }

    public BigDecimal getBet() {
        return BigDecimal.valueOf(Double.parseDouble(get(Property.BETTING_AMOUNT)));
    }

    public String getConfigFilePath() {
        return get(Property.CONFIG);
    }

    private String get(Property property) {
        return properties.get(property);
    }
}
