package com.freeuni.quizzard.api;

import com.freeuni.quizzard.common.api.openapi.error.OpenApi401ErrorResponse;
import com.freeuni.quizzard.common.api.openapi.error.OpenApi403ErrorResponse;
import com.freeuni.quizzard.common.api.openapi.error.OpenApi404CartErrorResponse;
import com.freeuni.quizzard.common.api.openapi.error.OpenApi500ErrorResponse;
import com.freeuni.quizzard.dto.UserDto;
import com.freeuni.quizzard.model.UserCreationAttributes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@OpenApi401ErrorResponse
@OpenApi403ErrorResponse
@OpenApi500ErrorResponse
@RequestMapping(UserApi.USER_URL)
public interface UserApi {

    String USER_URL = "/user";

    @Operation(summary = "Add a new user")
    @ApiResponse(responseCode = "201", description = "User was created successfully")
    @PostMapping("/create")
    ResponseEntity<String> addUser(UserCreationAttributes user);

    @Operation(summary = "Upload user picture")
    @ApiResponse(responseCode = "201", description = "Picture was updated successfully")
    @PostMapping("/picture")
    String uploadUser(MultipartFile file, String username);

    @Operation(summary = "Get user picture by username")
    @GetMapping("/picture/{username}")
    ResponseEntity<byte[]> getPicture(String username);

    @Operation(summary = "Get user email", description = "This is a secured endpoint.")
    @GetMapping("/email")
    String getEmail(Principal principal);

    @Operation(summary = "Get user username", description = "This is a secured endpoint.")
    @GetMapping("/username")
    String getUsername(Principal principal);

    @Operation(summary = "Get user profile by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully",
                         content = @Content(schema = @Schema(implementation = UserDto.class)))
    })
    @OpenApi404CartErrorResponse
    @GetMapping("/profile/{username}")
    ResponseEntity<UserDto> getUser(String username);

    @Operation(summary = "Get users by username prefix")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                 content = @Content(schema = @Schema(implementation = UserDto.class)))
    @GetMapping
    ResponseEntity<List<UserDto>> getUsers(String usernamePrefix);
}
