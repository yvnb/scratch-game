package com.teetov.scratch.in.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.in.dto.GameConfig;

import java.io.File;
import java.io.IOException;

public class ConfigReader {
    private final ObjectMapper mapper;

    public ConfigReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public GameConfig readConfig(String pathToFile) {
        File file = new File(pathToFile);
        try {
            return mapper.readValue(file, GameConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ScratchGameException("Reading config file failed");
        }
    }
}
