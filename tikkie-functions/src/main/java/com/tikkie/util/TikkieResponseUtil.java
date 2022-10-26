package com.tikkie.util;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tikkie.error.TikkieError;
import com.tikkie.response.TikkieResponse;

import java.util.List;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public final class TikkieResponseUtil {

    private final LambdaLogger lambdaLogger;

    private TikkieResponseUtil(LambdaLogger lambdaLogger) {

        this.lambdaLogger = lambdaLogger;
    }

    public static TikkieResponseUtil ofLogger(LambdaLogger lambdaLogger) {

        return new TikkieResponseUtil(lambdaLogger);
    }

    public <T> APIGatewayProxyResponseEvent returnCreated(T responseBody) {

        return returnApiResponse(HttpStatusUtil.CREATED, responseBody, null);
    }

    public <T> APIGatewayProxyResponseEvent returnDuplication(T responseBody) {

        return returnApiResponse(HttpStatusUtil.CONFLICT, responseBody, null);
    }

    public <T> APIGatewayProxyResponseEvent returnOk(T responseBody) {

        return returnApiResponse(HttpStatusUtil.OK, responseBody, null);
    }

    public <T> APIGatewayProxyResponseEvent returnNoContent(T responseBody) {

        return returnApiResponse(HttpStatusUtil.NO_CONTENT, responseBody, null);
    }

    public <T> APIGatewayProxyResponseEvent returnNotFound(T responseBody) {

        return returnApiResponse(HttpStatusUtil.NOT_FOUND, responseBody, null);
    }

    public <T> APIGatewayProxyResponseEvent returnInternalServerError(T responseBody) {

        return returnApiResponse(HttpStatusUtil.INTERNAL_SERVER_ERROR, responseBody, List.of(TikkieError.INTERNAL_SERVER_ERROR));
    }

    public <T> APIGatewayProxyResponseEvent returnBadRequest(T responseBody, List<TikkieError> tikkieErrors) {

        return returnApiResponse(HttpStatusUtil.BAD_REQUEST, responseBody, tikkieErrors);
    }

    private <T> APIGatewayProxyResponseEvent returnApiResponse(int statusCode, T responseBody, List<TikkieError> tikkieErrors) {

        try {
            final TikkieResponse<Object> tikkieResponse = TikkieResponse.builder().responseBody(responseBody).errors(tikkieErrors).build();
            final String tikkieResponseBody = JacksonUtil.OBJECT_MAPPER.writeValueAsString(tikkieResponse);
            APIGatewayProxyResponseEvent responseEvent = createApiGatewayProxyResponseEvent(statusCode, tikkieResponseBody);

            lambdaLogger.log(String.format("%n%s", responseEvent.toString()));

            return responseEvent;
        } catch (JsonProcessingException jsonProcessingException) {
            return createApiGatewayProxyResponseEvent(HttpStatusUtil.INTERNAL_SERVER_ERROR, jsonProcessingException.getMessage());
        }
    }

    private static APIGatewayProxyResponseEvent createApiGatewayProxyResponseEvent(final int statusCode, final String responseBody) {

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withBody(responseBody)
                .withIsBase64Encoded(false);
    }

}
