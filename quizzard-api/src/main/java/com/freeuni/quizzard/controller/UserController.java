package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.dto.UserDto;
import com.freeuni.quizzard.exception.UserAlreadyExistsException;
import com.freeuni.quizzard.model.FriendRequest;
import com.freeuni.quizzard.model.UserCreationAttributes;
import com.freeuni.quizzard.service.FriendRequestService;
import com.freeuni.quizzard.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.freeuni.quizzard.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> addUser(@RequestBody UserCreationAttributes user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).
                body("User was created successfully");
    }

    @PostMapping("/picture")
    public String uploadUser(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
        return userService.upload(file, username);
    }

    @GetMapping("/picture/{username}")
    public ResponseEntity<byte[]> getPicture(@PathVariable String username) {
        return ResponseEntity.ok(userService.getProfilePictureByName(username));
    }

    // secured endpoint
    @GetMapping("/email")
    public String getEmail(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String email = (String) token.getTokenAttributes().get("email");
        return email;
    }

    @GetMapping("/username")
    public String getUsername(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String username = (String) token.getTokenAttributes().get("preferred_username");
        return username;
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        UserDto userDto = userService.getUser(username);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(userDto);
        }
    }

    @GetMapping
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
