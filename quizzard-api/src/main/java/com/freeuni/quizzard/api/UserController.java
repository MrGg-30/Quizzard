package com.freeuni.quizzard.api;

import com.freeuni.quizzard.dto.UserDto;
import com.freeuni.quizzard.exception.UserAlreadyExistsException;
import com.freeuni.quizzard.model.UserCreationAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.freeuni.quizzard.service.UserService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    public ResponseEntity<String> addUser(@RequestBody UserCreationAttributes user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).
                body("User was created successfully");
    }

    public String uploadUser(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
        return userService.upload(file, username);
    }

    public ResponseEntity<byte[]> getPicture(@PathVariable String username) {
        return ResponseEntity.ok(userService.getProfilePictureByName(username));
    }

    public String getEmail(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String email = (String) token.getTokenAttributes().get("email");
        return email;
    }

    public String getUsername(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String username = (String) token.getTokenAttributes().get("preferred_username");
        return username;
    }

    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        UserDto userDto = userService.getUser(username);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(userDto);
        }
    }

    public ResponseEntity<UserDto> getUserByToken(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String username = (String) token.getTokenAttributes().get("preferred_username");

        UserDto userDto = userService.getUser(username);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(userDto);
        }
    }


    public ResponseEntity<List<UserDto>> getUsers(@RequestParam String usernamePrefix) {
        List<UserDto> users = userService.getUserByUsernamePrefix(usernamePrefix);

        return ResponseEntity.status(HttpStatus.OK).
                body(users);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleDuplicateResourceException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
