package com.poolc.springproject.poolcreborn.util;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

import java.nio.charset.StandardCharsets;

// @AutoconfigureMockMvc를 사용하여 MockMvc를 주입하는 경우에 한해서만 적용
@Component
class MockMvcCharacterEncodingCustomizer implements MockMvcBuilderCustomizer {
    @Override
    public void customize(ConfigurableMockMvcBuilder<?> builder) {
        builder.alwaysDo(result -> result.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()));
    }
}
