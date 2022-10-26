package com.tikkie.validator;

import com.tikkie.error.TikkieError;
import com.tikkie.request.CreatePersonRequest;
import software.amazon.awssdk.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public final class PersonValidator {

    private PersonValidator() {

        throw new IllegalStateException("Utility class");
    }

    public static List<TikkieError> validateCreatePersonRequest(final CreatePersonRequest createPersonRequest) {

        List<TikkieError> tikkieErrors = new ArrayList<>();

        if (Objects.isNull(createPersonRequest)) {
            tikkieErrors.add(TikkieError.CREATE_PERSON_REQUEST_JSON_MUST_NOT_NULL);
        } else {
            if (StringUtils.isBlank(createPersonRequest.getFirstname())) {
                tikkieErrors.add(TikkieError.PERSON_FIRSTNAME_MUST_NOT_NULL_OR_BLANK);
            }
            if (StringUtils.isBlank(createPersonRequest.getLastname())) {
                tikkieErrors.add(TikkieError.PERSON_LASTNAME_MUST_NOT_NULL_OR_BLANK);
            }
            if (StringUtils.isBlank(createPersonRequest.getPhoneNumber())) {
                tikkieErrors.add(TikkieError.PERSON_PHONE_NUMBER_MUST_NOT_NULL_OR_BLANK);
            }
            if (Objects.isNull(createPersonRequest.getAddress())) {
                tikkieErrors.add(TikkieError.PERSON_ADDRESS_MUST_NOT_NULL);
            } else {
                if (StringUtils.isBlank(createPersonRequest.getAddress().getCountry())) {
                    tikkieErrors.add(TikkieError.PERSON_ADDRESS_COUNTRY_MUST_NOT_NULL_OR_BLANK);
                }
            }
        }

        return tikkieErrors;
    }

}
