package com.teetov.scratch.out.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teetov.scratch.out.dto.GameResult;

public class ConsoleGameOutput {

    private final ObjectMapper objectMapper;

    public ConsoleGameOutput(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void print(GameResult gameResult) {
        try {
            System.out.println(objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(gameResult)
            );
        } catch (JsonProcessingException e) {
            System.out.println("Game result serialization failed");
            e.printStackTrace();
        }
    }
}
