package com.tikkie.util;

import com.tikkie.model.Person;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public final class DynamoDbUtil {

    private DynamoDbUtil() {

        throw new IllegalStateException("Utility Class");
    }

    private static final String CONFIG_REGION = System.getenv("AWS_REGION");

    private static final Region REGION = CONFIG_REGION != null ? Region.of(CONFIG_REGION) : Region.US_EAST_1;

    private static final String PERSON_TABLE_NAME = System.getenv("PERSON_DYNAMODB_TABLE_NAME");

    private static DynamoDbEnhancedClient createDynamoDbEnhancedClient() {

        final DynamoDbClient ddb = DynamoDbClient.builder().region(REGION).build();

        return DynamoDbEnhancedClient.builder().dynamoDbClient(ddb).build();
    }

    public static DynamoDbTable<Person> getPersonTable() {

        return createDynamoDbEnhancedClient().table(PERSON_TABLE_NAME, TableSchema.fromBean(Person.class));
    }

}
