package com.freeuni.quizzard.api.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zalando.problem.Problem;

import java.util.ArrayList;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "The wrapper of array of occurred errors during request execution")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    @Schema(description = "The array of occurred errors during request execution",
            requiredMode = REQUIRED, requiredProperties = "type")
    @JsonProperty("errors")
    @Builder.Default
    private List<Problem> problems = new ArrayList<>();

}