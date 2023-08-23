package com.freeuni.quizzard.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/swagger-ui/**",
                        "/swagger-resources/*",
                        "/v3/api-docs/**")
                .permitAll()

                .requestMatchers(HttpMethod.GET, "/**", "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/**", "/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/**", "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/create", "/user/create/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/app/*", "/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/quiz/**", "/quiz/questions/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/leaderboard/**", "/leaderboard/rating/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/quiz/**", "/quiz/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/user", "/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/email","/user/email/**").hasAnyRole(ADMIN,USER)
                 // TODO add endpoints to secure based on roles
                .anyRequest().authenticated().and().logout().permitAll();
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable();
        return http.build();
    }

}
