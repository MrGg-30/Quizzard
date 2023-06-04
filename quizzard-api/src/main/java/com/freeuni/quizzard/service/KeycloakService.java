package com.freeuni.quizzard.service;

import com.freeuni.quizzard.exception.UserAlreadyExistsException;
import com.freeuni.quizzard.model.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KeycloakService {

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

    private final Keycloak keycloakAdmin;

    private static final List<UserCredentials> USER_CREDENTIALS = Arrays.asList(
            new UserCredentials("admin","admin@admin.com", "admin"),
            new UserCredentials("user", "user@user.com","user"));

    public void createNewUser(String username, String email, String password) {
        if(isUsernameUnique(username) && isEmailUnique(email)) {
            UserRepresentation user = createUserRepresentation(new UserCredentials(username, email, password));
            keycloakAdmin.realm(quizzardRealm).users().create(user);
        } else {
            throw new UserAlreadyExistsException("Username Or Email is already used");
        }
    }

    public Optional<RealmRepresentation> findRealmByName(String realmName) {
        return keycloakAdmin.realms()
                .findAll()
                .stream()
                .filter(r -> r.getRealm().equals(realmName))
                .findAny();
    }

    public RealmRepresentation createRealm() {
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm(quizzardRealm);
        realmRepresentation.setEnabled(true);
        realmRepresentation.setRegistrationAllowed(true);
        realmRepresentation.setClients(List.of(createClient()));
        realmRepresentation.setUsers(createUserRepresentations());
        return realmRepresentation;
    }

    private boolean isEmailUnique(String email) {
        List<UserRepresentation> users = keycloakAdmin.realm(quizzardRealm).users().searchByEmail(email, true);
        return users.isEmpty();
    }

    private boolean isUsernameUnique(String username) {
        List<UserRepresentation> users = keycloakAdmin.realm(quizzardRealm).users().search(username);
        return users.isEmpty();
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

    private UserRepresentation createUserRepresentation(UserCredentials userPass) {
        CredentialRepresentation credentialRepresentation = createCredentialRepresentation(userPass);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userPass.username());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(userPass.email());
        userRepresentation.setEmailVerified(true);
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

    private List<UserRepresentation> createUserRepresentations() {
        return USER_CREDENTIALS.stream()
                .map(this::createUserRepresentation)
                .toList();
    }
}
