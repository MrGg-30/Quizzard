package com.freeuni.quizzard.common.api.openapi.error;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class OpenApiErrorResponseExamples {

    public static final String INTERNAL_SERVER_ERROR_EXAMPLE =
            """
            {
              "errors": [
                {
                  "type": "/internal-server-error",
                  "title": "Unexpected internal server error",
                  "status": 500,
                  "detail": "Unexpected internal server error occurred. Please contact service administrator for more details."
                }
              ]
            }
            """;

    public static final String EXTERNAL_SERVER_ERROR_EXAMPLE =
            """
            {
               "errors": [
                 {
                   "type": "/external-server-error",
                   "title": "Unexpected external error",
                   "status": 500,
                   "detail": "Unexpected external server error occurred. Please contact service administrator for more details.",
                   "source": "CommerceTools"
                 }
               ]
             }
            """;

    public static final String INVALID_REQUEST_ERROR_EXAMPLE =
            """
            {
               "errors": [
                 {
                   "type": "/validation-failed/invalid-request",
                   "title": "Request validation failed",
                   "status": 400,
                   "detail": "The request is invalid. Check API specification to verify if all required request elements are provided and correct."
                 }
               ]
             }
            """;

    public static final String CONSTRAINT_VALIDATION_ERROR_EXAMPLE =
            """
            {
              "errors": [
                {
                  "type": "/validation-failed/constraint-violation",
                  "title": "Constraint Violation",
                  "status": 400,
                  "detail": "Constraint violation(s) occurred during request validation. Check violation(s) for more details",
                  "violations": [
                    {
                      "field": "path.to.the.field1",
                      "message": "must not be null"
                    }
                  ]
                }
              ]
            }
            """;


    public static final String NOT_FOUND_LINE_EXAMPLE =
            """
            {
              "errors": [
                {
                  "type": "/resource-not-found",
                  "title": "Resource not found",
                  "status": 404,
                  "detail": "The specified customer has no requested resource",
                  "resource": "line"
                }
              ]
            }
            """;


    public static final String SOURCE_ACCESS_FORBIDDEN_ERROR_EXAMPLE =
            """
            {
               "errors": [
                 {
                   "type": "/source-access-forbidden",
                   "title": "Request validation failed",
                   "status": 400,
                   "detail": "The user has no permissions to view the requested source"
                 }
               ]
             }
            """;
    public static final String NOT_FOUND_EXAMPLE =
            """
            {
               "errors": [
                 {
                   "type": "/resource-not-found",
                   "title": "Resource not found",
                   "status": 404,
                   "detail": "Specified resource was not found"
                 }
               ]
             }
            """;
}
