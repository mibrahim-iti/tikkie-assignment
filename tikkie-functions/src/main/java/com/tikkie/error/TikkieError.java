package com.tikkie.error;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
@Getter
@ToString
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TikkieError {

    INVALID_JSON_REQUEST("0001", "invalid.json.request"),
    CREATE_PERSON_REQUEST_JSON_MUST_NOT_NULL("0002", "create.person.json.must.not.null"),
    PERSON_FIRSTNAME_MUST_NOT_NULL_OR_BLANK("0003", "person.firstname.must.not.null.or.blank"),
    PERSON_LASTNAME_MUST_NOT_NULL_OR_BLANK("0004", "person.lastname.must.not.null.or.blank"),
    PERSON_PHONE_NUMBER_MUST_NOT_NULL_OR_BLANK("0005", "person.phoneNumber.must.not.null.or.blank"),
    PERSON_ADDRESS_MUST_NOT_NULL("0006", "person.address.must.not.null"),
    PERSON_ADDRESS_COUNTRY_MUST_NOT_NULL_OR_BLANK("0007", "person.address.country.must.not.null"),
    INTERNAL_SERVER_ERROR("1001", "internal.server.error");


    private String errorCode;

    private String messageKey;
}
