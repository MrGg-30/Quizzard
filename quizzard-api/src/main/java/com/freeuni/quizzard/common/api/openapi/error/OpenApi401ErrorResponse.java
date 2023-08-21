package com.freeuni.quizzard.common.api.openapi.error;

import com.freeuni.quizzard.common.api.model.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponse(
        responseCode = "401",
        description = "The unauthorized request was sent",
        content = @Content(
                mediaType = APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ApiErrorResponse.class),
                examples = {
                        @ExampleObject(
                                name = "unauthorized",
                                value = """
                                        {
                                          "errors": [
                                            {
                                              "type": "/unauthorized",
                                              "title": "Unauthorized",
                                              "status": 401,
                                              "detail": "The request lacks valid authentication credentials for the requested resource"
                                            }
                                          ]
                                        }
                                        """
                        )
                }
        ))
public @interface OpenApi401ErrorResponse {

}
