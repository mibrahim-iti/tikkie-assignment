package com.tikkie.mapper;

import com.tikkie.model.Person;
import com.tikkie.request.CreatePersonRequest;
import com.tikkie.util.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
@Mapper
public interface PersonMapper {

    @Mapping(target = "id", expression = "java(generateRandomId())")
    @Mapping(target = "created", expression = "java(handleCreated())")
    @Mapping(target = "fullName", source = ".", qualifiedByName = "toFullName")
    Person toPerson(final CreatePersonRequest createPersonRequest);

    @Named("toFullName")
    default String toFullName(final CreatePersonRequest createPersonRequest) {

        return createPersonRequest.getFirstname() + Constants.FULL_NAME_DELIMITER + createPersonRequest.getLastname();
    }

    default String generateRandomId() {

        return UUID.randomUUID().toString();
    }

    default Instant handleCreated() {

        return Instant.now();
    }

}
