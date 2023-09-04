package com.freeuni.quizzard.api;

import com.freeuni.quizzard.common.api.openapi.error.OpenApi401ErrorResponse;
import com.freeuni.quizzard.common.api.openapi.error.OpenApi403ErrorResponse;
import com.freeuni.quizzard.common.api.openapi.error.OpenApi500ErrorResponse;
import com.freeuni.quizzard.model.FriendRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@OpenApi401ErrorResponse
@OpenApi403ErrorResponse
@OpenApi500ErrorResponse
public interface  FriendRequestApi {

    @Operation(summary = "Get received pending friend requests",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Received pending friend requests retrieved successfully",
                         content = @Content(schema = @Schema(implementation = List.class)))
    })
    @GetMapping("/pendingRequests/{username}")
    ResponseEntity<List<FriendRequest>> getReceivedPendingRequests(@PathVariable String username);

    @Operation(summary = "Send friend request",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/friends/request")
    void sendFriendRequest(@RequestBody FriendRequest request);

    @Operation(summary = "Send friend response",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/friends/response")
    void respondFriendRequest(@RequestBody FriendRequest request);
}
