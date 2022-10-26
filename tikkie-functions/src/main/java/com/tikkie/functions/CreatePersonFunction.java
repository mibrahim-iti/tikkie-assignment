package com.tikkie.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tikkie.dao.PersonDynamoDbDao;
import com.tikkie.error.TikkieError;
import com.tikkie.mapper.PersonMapperImpl;
import com.tikkie.model.Person;
import com.tikkie.request.CreatePersonRequest;
import com.tikkie.service.PersonService;
import com.tikkie.util.DynamoDbUtil;
import com.tikkie.util.JacksonUtil;
import com.tikkie.util.TikkieResponseUtil;
import com.tikkie.validator.PersonValidator;
import software.amazon.awssdk.utils.CollectionUtils;
import software.amazon.awssdk.utils.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public class CreatePersonFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final PersonService personService;

    public CreatePersonFunction() {

        personService = new PersonService(new PersonMapperImpl(), new PersonDynamoDbDao(DynamoDbUtil.getPersonTable()));
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {

        final LambdaLogger logger = context.getLogger();
        final TikkieResponseUtil tikkieResponseUtil = TikkieResponseUtil.ofLogger(logger);

        try {
            CreatePersonRequest createPersonRequest = deserializeBodyToCreatePersonRequest(requestEvent.getBody());

            final List<TikkieError> tikkieErrors = PersonValidator.validateCreatePersonRequest(createPersonRequest);
            if (!CollectionUtils.isNullOrEmpty(tikkieErrors)) {
                return tikkieResponseUtil.returnBadRequest("Invalid Person Request", tikkieErrors);
            }

            final Optional<Person> optionalPerson = personService.find(createPersonRequest.getFirstname(), createPersonRequest.getLastname());
            if (optionalPerson.isPresent()) {
                return tikkieResponseUtil.returnDuplication("The person with same firstname and lastname already exist.");
            }

            final String personId = personService.save(createPersonRequest);

            return tikkieResponseUtil.returnCreated(personId);

        } catch (JsonProcessingException exception) {
            logger.log(String.format("Exception while reading body %s: %s", exception, exception.getStackTrace()));
            return tikkieResponseUtil.returnBadRequest("Invalid JSON request", List.of(TikkieError.INVALID_JSON_REQUEST));
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.log(String.format("Exception %s: %s", exception, exception.getStackTrace()));
            return tikkieResponseUtil.returnInternalServerError(exception.toString());
        }
    }

    private CreatePersonRequest deserializeBodyToCreatePersonRequest(String requestBody) throws JsonProcessingException {

        if (StringUtils.isNotBlank(requestBody)) {
            return JacksonUtil.OBJECT_MAPPER.readValue(requestBody, CreatePersonRequest.class);
        }

        return null;
    }

}