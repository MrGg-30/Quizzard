package com.freeuni.quizzard.api;

import com.freeuni.quizzard.common.api.openapi.error.OpenApi401ErrorResponse;
import com.freeuni.quizzard.common.api.openapi.error.OpenApi403ErrorResponse;
import com.freeuni.quizzard.common.api.openapi.error.OpenApi500ErrorResponse;
import com.freeuni.quizzard.dto.LeaderboardDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@OpenApi401ErrorResponse
@OpenApi403ErrorResponse
@OpenApi500ErrorResponse
@RequestMapping(LeaderboardApi.LEADERBOARD_URL)
public interface LeaderboardApi {

    String LEADERBOARD_URL = "/leaderboard";

    @Operation(summary = "Get sorted leaderboard by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leaderboard retrieved successfully",
                         content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "Leaderboard not found")
    })
    @GetMapping("/rating/{category}")
    ResponseEntity<List<LeaderboardDto>> getSortedLeaderboard(@PathVariable String category);
}
