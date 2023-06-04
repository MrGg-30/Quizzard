package com.freeuni.quizzard.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdmin {

    @Bean
    public Keycloak createKeycloakAdministrator() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8090")
                .realm("master")
                .username("admin")
                .password("admin")
                .clientId("admin-cli")
                .build();
    }
}
