package com.freeuni.quizzard.config;

import com.freeuni.quizzard.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KeycloakConfig implements CommandLineRunner {

    private final Keycloak keycloakAdmin;
    private final KeycloakService keycloakService;

    @Value("${quizzard.realm}")
    private String quizzardRealm;

    @Override
    public void run(String... args) {
        Optional<RealmRepresentation> representationOptional = keycloakService.findRealmByName(quizzardRealm);
        representationOptional.ifPresent(realmRepresentation -> keycloakAdmin.realm(quizzardRealm).remove());
        RealmRepresentation realmRepresentation = keycloakService.createRealm();
        keycloakAdmin.realms().create(realmRepresentation);
    }
}
