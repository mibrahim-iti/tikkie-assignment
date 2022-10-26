package com.tikkie.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Address {

    private String country;

    @DynamoDbAttribute("Country")
    public String getCountry() {

        return country;
    }

}
