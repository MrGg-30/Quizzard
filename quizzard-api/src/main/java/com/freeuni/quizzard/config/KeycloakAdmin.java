package com.freeuni.quizzard.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdmin {

    @Value("${keycloak.serverUrl}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Bean
    public Keycloak createKeycloakAdministrator() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .username(username)
                .password(password)
                .clientId(clientId)
                .build();
    }
}
