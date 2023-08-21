package com.freeuni.quizzard.common.api.openapi.error;

import com.freeuni.quizzard.common.api.model.response.ApiErrorResponse;
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

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponse(
        responseCode = "403",
        description = "Current user is not authorised for requested action",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ApiErrorResponse.class),
                examples = @ExampleObject(
                        name = "access-forbidden",
                        value = """
                                {
                                   "errors": [
                                     {
                                       "type": "/access-forbidden",
                                       "title": "Access forbidden",
                                       "status": 403,
                                       "detail": "The user has no permissions to view the requested resource"
                                     }
                                   ]
                                 }
                                """
                )
        ))
public @interface OpenApi403ErrorResponse {

}
