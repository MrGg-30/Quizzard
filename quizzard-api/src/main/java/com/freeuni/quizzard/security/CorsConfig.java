package com.freeuni.quizzard.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");  // Allows all origins
        config.addAllowedMethod("*"); // Allows all methods (GET, POST, PUT, DELETE, etc.)
        config.addAllowedHeader("*"); // Allows all headers
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
