package com.teetov.scratch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Test
    void runApplication() {
        Assertions.assertDoesNotThrow(() ->
                Application.main("--config", "src/test/resources/configOk.json",
                        "--betting-amount", "300"));

    }
}
