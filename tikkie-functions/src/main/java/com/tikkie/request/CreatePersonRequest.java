package com.tikkie.request;

import lombok.Data;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
@Data
public class CreatePersonRequest {

    private String firstname;

    private String lastname;

    private String phoneNumber;

    private AddressRequest address;

}
