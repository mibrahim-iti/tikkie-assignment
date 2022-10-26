package com.tikkie.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.tikkie.dao.PersonDynamoDbDao;
import com.tikkie.mapper.PersonMapperImpl;
import com.tikkie.service.PersonService;
import com.tikkie.util.DynamoDbUtil;
import com.tikkie.util.TikkieResponseUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public class DeletePersonByIdFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final PersonService personService;

    public DeletePersonByIdFunction() {

        personService = new PersonService(new PersonMapperImpl(), new PersonDynamoDbDao(DynamoDbUtil.getPersonTable()));
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {

        final LambdaLogger logger = context.getLogger();
        final TikkieResponseUtil tikkieResponseUtil = TikkieResponseUtil.ofLogger(logger);

        try {
            final Map<String, String> pathParameters = requestEvent.getPathParameters();

            if (Objects.isNull(pathParameters) || pathParameters.isEmpty()) {
                return tikkieResponseUtil.returnBadRequest("Invalid Delete Person, no id found in the path parameter", null);
            }

            logger.log("pathParameters start");
            pathParameters.entrySet().forEach(entry -> {
                logger.log(entry.getKey() + " " + entry.getValue());
            });
            logger.log("pathParameters end");

            personService.delete(pathParameters.get("id"));

            return tikkieResponseUtil.returnNoContent("Successfully Deleted");

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.log(String.format("Exception %s: %s", exception, exception.getStackTrace()));
            return tikkieResponseUtil.returnInternalServerError(exception.toString());
        }
    }

}