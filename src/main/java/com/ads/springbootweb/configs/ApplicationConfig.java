package com.ads.springbootweb.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
