package com.bobbbaich.hitbtc.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UtilConfig {
    @Bean
    @Scope("prototype")
    public Gson gson() {
        return new GsonBuilder().create();
    }
}
