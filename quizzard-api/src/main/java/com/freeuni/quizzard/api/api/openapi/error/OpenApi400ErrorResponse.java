package com.freeuni.quizzard.api.api.openapi.error;

import com.freeuni.quizzard.api.api.model.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.freeuni.quizzard.api.api.openapi.error.OpenApiErrorResponseExamples.CONSTRAINT_VALIDATION_ERROR_EXAMPLE;
import static com.freeuni.quizzard.api.api.openapi.error.OpenApiErrorResponseExamples.INVALID_REQUEST_ERROR_EXAMPLE;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponse(
        responseCode = "400",
        description = "The malformed request was sent",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ApiErrorResponse.class),
                examples = {
                        @ExampleObject(
                                name = "invalid-request",
                                value = INVALID_REQUEST_ERROR_EXAMPLE
                        ),
                        @ExampleObject(
                                name = "constraint-violation",
                                value = CONSTRAINT_VALIDATION_ERROR_EXAMPLE
                        ),
                        @ExampleObject(
                                name = "not-unique-some-property",
                                value = """
                                        {
                                          "errors": [
                                            {
                                              "type": "/validation-failed/not-unique-property",
                                              "title": "Not unique property",
                                              "status": 400,
                                              "detail": "This is generic validation error example. Provided property already belongs to another cart"
                                            }
                                          ]
                                        }
                                        """
                        )
                }
        ))
public @interface OpenApi400ErrorResponse {

}
