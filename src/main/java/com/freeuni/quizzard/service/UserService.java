package com.freeuni.quizzard.service;

import com.freeuni.quizzard.model.TokenResponse;
import com.freeuni.quizzard.model.UserCreationAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakService keycloakService;

    @Value("${quizzard.app.clientId}")
    private String clientId;

    @Value("${quizzard.app.tokenEndpoint}")
    private String tokenEndpoint;

    public String createUser(UserCreationAttributes user) {
        keycloakService.createNewUser(user.getUsername(), user.getEmail(), user.getPassword());
        return generateJwtToken(user);
    }

    public String generateJwtToken(UserCreationAttributes credentials){
        MultiValueMap<String, String> requestBody = buildRequestBody(credentials);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<TokenResponse> responseEntity = restTemplate
                .exchange(tokenEndpoint, HttpMethod.POST, requestEntity, TokenResponse.class);
        TokenResponse tokenResponse = responseEntity.getBody();
        return tokenResponse != null ? tokenResponse.getAccessToken() : "";
    }

    private MultiValueMap<String, String> buildRequestBody(UserCreationAttributes credentials){
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", credentials.getUsername());
        requestBody.add("password", credentials.getPassword());
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", clientId);
        return requestBody;
    }
}
