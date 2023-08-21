package com.freeuni.quizzard.api;


import com.freeuni.quizzard.api.api.openapi.error.OpenApi401ErrorResponse;
import com.freeuni.quizzard.api.api.openapi.error.OpenApi403ErrorResponse;
import com.freeuni.quizzard.api.api.openapi.error.OpenApi500ErrorResponse;
import com.freeuni.quizzard.dto.UserDto;
import com.freeuni.quizzard.model.UserCreationAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@OpenApi401ErrorResponse
@OpenApi403ErrorResponse
@OpenApi500ErrorResponse
public interface UserApi {

    String USER_URL = "/user";

    ResponseEntity<String> addUser(UserCreationAttributes user);

    String uploadUser(MultipartFile file, String username);

    ResponseEntity<byte[]> getPicture(String username);

    String getEmail(Principal principal);

    String getUsername(Principal principal);

    ResponseEntity<UserDto> getUser(String username);

    ResponseEntity<List<UserDto>> getUsers(String usernamePrefix);

}
