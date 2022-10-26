package com.tikkie.util;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.tikkie.model.Address;
import com.tikkie.request.AddressRequest;
import com.tikkie.request.CreatePersonRequest;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public class PersonMock {

//    public static APIGatewayProxyRequestEvent createPersonRequest() {
//
//        final CreatePersonRequest createPersonRequest = new CreatePersonRequest();
//
//        return new APIGatewayProxyRequestEvent().withPath("/person").withBody(createPersonRequest.toString());
//    }

    public static CreatePersonRequest createEmptyPersonRequest() {

        return new CreatePersonRequest();
    }

    public static CreatePersonRequest createPersonRequest() {

        final CreatePersonRequest createPersonRequest = createEmptyPersonRequest();

        createPersonRequest.setFirstname("Tikkie");
        createPersonRequest.setLastname("Tikkie");
        createPersonRequest.setPhoneNumber("+20123456789");
        createPersonRequest.setAddress(createAddressRequest());

        return createPersonRequest;
    }

    private static AddressRequest createAddressRequest() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCountry("My Country");
        return addressRequest;
    }

}
