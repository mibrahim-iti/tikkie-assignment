package com.tikkie.converter;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.InstantAsStringAttributeConverter;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.Instant;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public class InstantCreatedConverter implements AttributeConverter<Instant> {

    private static final InstantAsStringAttributeConverter converter = InstantAsStringAttributeConverter.create();

    @Override
    public AttributeValue transformFrom(final Instant input) {

        return converter.transformFrom(input);
    }

    @Override
    public Instant transformTo(final AttributeValue input) {

        return converter.transformTo(input);
    }

    @Override
    public EnhancedType<Instant> type() {

        return converter.type();
    }

    @Override
    public AttributeValueType attributeValueType() {

        return converter.attributeValueType();
    }

}
