package com.bobbbaich.hitbtc.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class UtilConfig {
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Bean
    public Gson gson() {
        return new GsonBuilder().setDateFormat(DATE_FORMAT).create();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.dateFormat(new SimpleDateFormat(DATE_FORMAT));
    }
}
