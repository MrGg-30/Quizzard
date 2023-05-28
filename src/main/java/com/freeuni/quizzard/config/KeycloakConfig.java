package com.freeuni.quizzard.config;

import com.freeuni.quizzard.model.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KeycloakConfig implements CommandLineRunner {

    private final Keycloak keycloakAdmin;

    @Value("${quizzard.realm}")
    private String quizzardRealm;

    @Value("${quizzard.app.clientId}")
    private String clientId;

    @Value("${quizzard.app.redirectUrl}")
    private String redirectUrl;

    @Value("${quizzard.userRole}")
    private String userRole;

    @Value("${quizzard.adminRole}")
    private String adminRole;

    private static final List<UserCredentials> MOVIES_APP_USERS = Arrays.asList(
            new UserCredentials("admin", "admin"),
            new UserCredentials("user", "user"));

    @Override
    public void run(String... args) {
        Optional<RealmRepresentation> representationOptional = findRealmByName(quizzardRealm);
        representationOptional.ifPresent(realmRepresentation -> keycloakAdmin.realm(quizzardRealm).remove());

        RealmRepresentation realmRepresentation = createRealm();
        ClientRepresentation clientRepresentation = createClient();
        realmRepresentation.setClients(List.of(clientRepresentation));
        List<UserRepresentation> userRepresentations = createUserRepresentations();
        realmRepresentation.setUsers(userRepresentations);

        keycloakAdmin.realms().create(realmRepresentation);
    }

    private Optional<RealmRepresentation> findRealmByName(String realmName) {
        return keycloakAdmin.realms()
                .findAll()
                .stream()
                .filter(r -> r.getRealm().equals(realmName))
                .findAny();
    }

    private RealmRepresentation createRealm() {
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm(quizzardRealm);
        realmRepresentation.setEnabled(true);
        realmRepresentation.setRegistrationAllowed(true);
        return realmRepresentation;
    }

    private ClientRepresentation createClient() {
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(clientId);
        clientRepresentation.setDirectAccessGrantsEnabled(true);
        clientRepresentation.setPublicClient(true);
        clientRepresentation.setRedirectUris(List.of(redirectUrl));
        clientRepresentation.setDefaultRoles(new String[]{userRole});
        return clientRepresentation;
    }

    private List<UserRepresentation> createUserRepresentations() {
        return MOVIES_APP_USERS.stream()
                .map(this::createUserRepresentation)
                .toList();
    }

    private UserRepresentation createUserRepresentation(UserCredentials userPass) {
        CredentialRepresentation credentialRepresentation = createCredentialRepresentation(userPass);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userPass.username());
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        userRepresentation.setClientRoles(getClientRoles(userPass));

        return userRepresentation;
    }

    private CredentialRepresentation createCredentialRepresentation(UserCredentials userPass) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userPass.password());
        return credentialRepresentation;
    }

    private Map<String, List<String>> getClientRoles(UserCredentials userPass) {
        List<String> roles = new ArrayList<>();
        roles.add(userRole);
        if ("admin".equals(userPass.username())) {
            roles.add(adminRole);
        }
        return Map.of(clientId, roles);
    }
}
