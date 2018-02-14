package com.bobbbaich.hitbtc.config;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.dateFormat(new ISO8601DateFormat());
    }
}
